package com.microservices.analyzerservice.service;

import com.microservices.analyzerservice.model.analysis.SmartPlugHistory;
import com.microservices.analyzerservice.model.analysis.VacuumCleanerHistory;
import com.microservices.analyzerservice.model.raw.RawDeviceDataHistory;

public interface RawDeviceDataHistoryMapperService {

    SmartPlugHistory mapToSmartPlugAnalysisModel(RawDeviceDataHistory rawDeviceDataHistory);

    VacuumCleanerHistory mapToVacuumCleanerAnalysisModel(RawDeviceDataHistory rawDeviceDataHistory);
}
