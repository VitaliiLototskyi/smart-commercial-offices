package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.model.DevicePropertyValueDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevicePropertyValueDetailsRepository extends JpaRepository<DevicePropertyValueDetails, Long> {

    Optional<DevicePropertyValueDetails> findByDeviceAndProperty(Device device, String property);

    List<DevicePropertyValueDetails> findByDevice(Device device);
}
