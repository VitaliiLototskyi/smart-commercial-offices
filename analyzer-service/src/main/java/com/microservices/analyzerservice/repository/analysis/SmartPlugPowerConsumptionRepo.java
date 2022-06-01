package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.Device;
import com.microservices.analyzerservice.model.analysis.SmartPlugPowerConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmartPlugPowerConsumptionRepo extends JpaRepository<SmartPlugPowerConsumption, Long> {

    @Query("select max(c.endTime) from SmartPlugPowerConsumption c where c.device = :device")
    Optional<LocalDateTime> findMaxEndTimeOfDevice(Device device);
}
