package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.model.DeviceValueProperty;
import com.microservices.datageneratorservice.model.SmartPlug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceValuePropertyRepository extends JpaRepository<DeviceValueProperty, Long> {

    DeviceValueProperty findByDeviceAndValueName(Device device, String valueName);
}
