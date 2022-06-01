package com.microservices.analyzerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledJobsExecutorService {

    private final RawDeviceDataTransferService rawDeviceDataTransferService;
    private final SmartPlugDataAnalyzerService smartPlugDataAnalyzerService;

    @Scheduled(fixedRate = 300_000)
    public void transferRawDataIntoAnalysisDB() {
        this.rawDeviceDataTransferService.transferRawDataIntoAnalysisDB();
    }

    @Scheduled(fixedRate = 60_000)
    public void smartPlugDataAnalyzerServiceAnalyzeData() {
        this.smartPlugDataAnalyzerService.analyzeData();
    }
}
