package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    @Query("select d.uuid from Device d")
    List<String> findDeviceIDList();

    @Query("select d.uuid from Device d where d.deviceType = :deviceType")
    List<String> findDeviceIDListByDeviceType(DeviceType deviceType);
}
