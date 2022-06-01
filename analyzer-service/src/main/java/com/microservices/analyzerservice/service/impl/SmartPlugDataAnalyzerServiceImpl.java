package com.microservices.analyzerservice.service.impl;

import com.microservices.analyzerservice.model.analysis.Device;
import com.microservices.analyzerservice.model.analysis.SmartPlugHistory;
import com.microservices.analyzerservice.model.analysis.TemporalRange;
import com.microservices.analyzerservice.repository.analysis.DeviceRepo;
import com.microservices.analyzerservice.repository.analysis.SmartPlugHistoryRepo;
import com.microservices.analyzerservice.repository.analysis.SmartPlugPowerConsumptionRepo;
import com.microservices.analyzerservice.service.SmartPlugDataAnalyzerService;
import com.microservices.analyzerservice.utility.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartPlugDataAnalyzerServiceImpl implements SmartPlugDataAnalyzerService {

    private final DeviceRepo deviceRepo;
    private final SmartPlugHistoryRepo historyRepo;
    private final SmartPlugPowerConsumptionRepo powerConsumptionRepo;

    @Override
    public void analyzeData() {
        log.info("Started SmartPlug analysis");
        LocalDateTime analysisStartTime = LocalDateTime.now();
        List<Device> deviceList = this.deviceRepo.findAll();
        for (Device curDevice : deviceList) {
            LocalDateTime latestAnalysisRecTime = powerConsumptionRepo.findMaxEndTimeOfDevice(curDevice).orElse(LocalDateTime.now().minusMonths(1));
            // process by hours
            List<TemporalRange<LocalDateTime>> hourChunks = DateTimeUtil.getDateTimeChunks(latestAnalysisRecTime, analysisStartTime.plusHours(1), HOURS);
            for (TemporalRange<LocalDateTime> hourChunk : hourChunks) {
                List<SmartPlugHistory> historyRecords =
                        this.historyRepo.findAllByDeviceAndDateProcessedBetweenOrderByDateProcessed(curDevice, hourChunk.getStart(), hourChunk.getEnd());
                Double totalWattPerHour = this.processTotalPowerPerHour(historyRecords);
                if (totalWattPerHour != 0.0) {
                    log.info("Device {} consumed {} watt*h from {} to {}", curDevice.getName(), totalWattPerHour, hourChunk.getStart(), hourChunk.getEnd());
                }
            }
        }
        log.info("SmartPlug analysis successfully finished");
    }

    private Double processTotalPowerPerHour(List<SmartPlugHistory> historyList) {
        Map<Double, Long> powerAndDurationMap = new HashMap<>();
        for (int i = 1; i < historyList.size(); i++) {
            SmartPlugHistory currentRec = historyList.get(i);
            SmartPlugHistory previousRec = historyList.get(i - 1);
            if (!Objects.equals(currentRec.getPower(), previousRec.getPower())) {
                continue;
            }
            long duration = ChronoUnit.MILLIS.between(previousRec.getDateProcessed(), currentRec.getDateProcessed());
            long existingPowerDuration = powerAndDurationMap.getOrDefault(currentRec.getPower(), 0L);
            powerAndDurationMap.put(currentRec.getPower(), existingPowerDuration + duration);
        }
        double totalWattHour = 0.0;
        for (Map.Entry<Double, Long> e : powerAndDurationMap.entrySet()) {
            totalWattHour += (e.getKey() / 60 * e.getValue() / 60_000);
        }
        return totalWattHour;
    }
}
