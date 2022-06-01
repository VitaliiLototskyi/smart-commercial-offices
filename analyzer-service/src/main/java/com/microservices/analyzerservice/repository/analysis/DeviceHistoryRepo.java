package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.DeviceHistory;
import com.microservices.analyzerservice.model.analysis.SmartPlugHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DeviceHistoryRepo extends JpaRepository<DeviceHistory, Long> {

    @Query("select max(dh.dateReceived) from DeviceHistory dh")
    LocalDateTime findMaxDateReceived();
}
