package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<Device, String> {
}
