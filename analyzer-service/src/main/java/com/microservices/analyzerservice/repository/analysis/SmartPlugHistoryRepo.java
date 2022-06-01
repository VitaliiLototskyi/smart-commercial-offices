package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.Device;
import com.microservices.analyzerservice.model.analysis.SmartPlugHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SmartPlugHistoryRepo extends JpaRepository<SmartPlugHistory, Long> {


    List<SmartPlugHistory> findAllByDeviceAndDateProcessedBetweenOrderByDateProcessed(Device device, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
