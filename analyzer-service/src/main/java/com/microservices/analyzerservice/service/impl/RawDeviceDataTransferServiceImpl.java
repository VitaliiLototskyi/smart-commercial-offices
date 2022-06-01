package com.microservices.analyzerservice.service.impl;

import com.microservices.analyzerservice.model.raw.RawDeviceDataHistory;
import com.microservices.analyzerservice.repository.analysis.DeviceHistoryRepo;
import com.microservices.analyzerservice.repository.analysis.SmartPlugHistoryRepo;
import com.microservices.analyzerservice.repository.analysis.VacuumCleanerHistoryRepo;
import com.microservices.analyzerservice.repository.raw.RawDeviceDataRepo;
import com.microservices.analyzerservice.service.RawDeviceDataHistoryMapperService;
import com.microservices.analyzerservice.service.RawDeviceDataTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RawDeviceDataTransferServiceImpl implements RawDeviceDataTransferService {

    private final RawDeviceDataRepo rawDeviceDataRepo;
    private final RawDeviceDataHistoryMapperService mapperService;
    private final DeviceHistoryRepo deviceHistoryRepo;
    private final SmartPlugHistoryRepo smartPlugHistoryRepo;
    private final VacuumCleanerHistoryRepo vacuumCleanerHistoryRepo;

    @Override
    public void transferRawDataIntoAnalysisDB() {
        log.info("Started transfer of raw device data");
        LocalDateTime maxDateReceived = this.deviceHistoryRepo.findMaxDateReceived();
        maxDateReceived = (maxDateReceived == null) ? LocalDateTime.now().minusYears(1000) : maxDateReceived;
        List<RawDeviceDataHistory> newRawDeviceDataRecords = this.rawDeviceDataRepo.findAllByDateReceivedAfter(maxDateReceived);

        long transferredDeviceHistoryCounter = 0;
        for (RawDeviceDataHistory curRawDataRec : newRawDeviceDataRecords) {
            switch (curRawDataRec.getDeviceType()) {
                case SMART_PLUG:
                    this.smartPlugHistoryRepo.save(this.mapperService.mapToSmartPlugAnalysisModel(curRawDataRec));
                    break;
                case VACUUM_CLEANER:
                    this.vacuumCleanerHistoryRepo.save(this.mapperService.mapToVacuumCleanerAnalysisModel(curRawDataRec));
                    break;
                default:
                    log.warn("Skipping raw device data because device type isn't supported by analyzer-service. Device type: {}. Data: {}",
                                    curRawDataRec.getDeviceType(), curRawDataRec);
                    continue;
            }
            transferredDeviceHistoryCounter++;
        }
        log.info("Successfully transferred {} raw device data records", transferredDeviceHistoryCounter);
    }
}
