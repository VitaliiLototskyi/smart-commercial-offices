package com.microservices.analyzerservice.service.impl;

import com.microservices.analyzerservice.model.analysis.Device;
import com.microservices.analyzerservice.model.analysis.DeviceHistory;
import com.microservices.analyzerservice.model.analysis.Office;
import com.microservices.analyzerservice.model.analysis.SmartPlugHistory;
import com.microservices.analyzerservice.model.analysis.VacuumCleanerHistory;
import com.microservices.analyzerservice.model.raw.RawDeviceDataHistory;
import com.microservices.analyzerservice.repository.analysis.DeviceRepo;
import com.microservices.analyzerservice.repository.analysis.OfficeRepo;
import com.microservices.analyzerservice.service.RawDeviceDataHistoryMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RawDeviceDataHistoryMapperServiceImpl implements RawDeviceDataHistoryMapperService {

    private final OfficeRepo officeRepo;
    private final DeviceRepo deviceRepo;


    @Override
    public SmartPlugHistory mapToSmartPlugAnalysisModel(final RawDeviceDataHistory rawDeviceDataHistory) {
        Map<String, String> propertyValueMap = rawDeviceDataHistory.getPropertyValueMap();

        SmartPlugHistory historyRec = (SmartPlugHistory) this.mapToDeviceHistoryAnalysisModel(rawDeviceDataHistory);
        historyRec.setPower(Double.parseDouble(propertyValueMap.get("power")));
        historyRec.setChipTemperature(Double.parseDouble(propertyValueMap.get("chipTemperature")));
        historyRec.setLedLight(propertyValueMap.get("ledLight").startsWith("1"));

        return historyRec;
    }

    @Override
    public VacuumCleanerHistory mapToVacuumCleanerAnalysisModel(RawDeviceDataHistory rawDeviceDataHistory) {
        Map<String, String> propertyValueMap = rawDeviceDataHistory.getPropertyValueMap();

        VacuumCleanerHistory historyRec = (VacuumCleanerHistory) this.mapToDeviceHistoryAnalysisModel(rawDeviceDataHistory);
        historyRec.setCleanedArea(Double.parseDouble(propertyValueMap.get("cleanedArea")));
        historyRec.setWaterLevel((int) Double.parseDouble(propertyValueMap.get("waterLevel")));
        historyRec.setFanLevel((int) Double.parseDouble(propertyValueMap.get("fanLevel")));
        historyRec.setMopInstalled(propertyValueMap.get("mopInstalled").startsWith("1"));
        historyRec.setCharging(propertyValueMap.get("isCharging").startsWith("1"));
        historyRec.setWaterTankUsagePct((int) Double.parseDouble(propertyValueMap.get("waterTankUsagePct")));
        historyRec.setGarbageTankUsagePct((int) Double.parseDouble(propertyValueMap.get("garbageTankUsagePct")));

        return historyRec;
    }

    private DeviceHistory mapToDeviceHistoryAnalysisModel(RawDeviceDataHistory rawDeviceDataHistory) {
        Map<String, String> propertyValueMap = rawDeviceDataHistory.getPropertyValueMap();

        return SmartPlugHistory.builder()
                .device(this.saveDeviceAndOffice(rawDeviceDataHistory))
                .signalStrength(Double.parseDouble(propertyValueMap.get("signalStrength")))
                .turnedOn(propertyValueMap.get("turnedOn").startsWith("1"))
                .workingTimeMin((int) Double.parseDouble(propertyValueMap.get("workingTimeMin")))
                .dateReceived(rawDeviceDataHistory.getDateReceived())
                .updateDateTime(rawDeviceDataHistory.getUpdateDateTime())
                .dateProcessed(LocalDateTime.now())
                .build();
    }

    private Device saveDeviceAndOffice(RawDeviceDataHistory rawDeviceDataHistory) {
        Office office = this.officeRepo.findByName(rawDeviceDataHistory.getOfficeName())
                .orElse(Office.builder()
                        .name(rawDeviceDataHistory.getOfficeName())
                        .isAuthenticated(true)
                        .build());
        this.officeRepo.save(office);

        Device device = this.deviceRepo.findById(rawDeviceDataHistory.getUuid())
                .orElse(Device.builder()
                        .uuid(rawDeviceDataHistory.getUuid())
                        .name(rawDeviceDataHistory.getName())
                        .modelName(rawDeviceDataHistory.getModelName())
                        .deviceType(rawDeviceDataHistory.getDeviceType())
                        .connectionType(rawDeviceDataHistory.getConnectionType())
                        .office(office)
                        .build());
        return this.deviceRepo.save(device);
    }
}
