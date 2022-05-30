package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.DevicePropertyImitationRule;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevicePropertyImitationRuleRepository extends JpaRepository<DevicePropertyImitationRule, Long> {

    DevicePropertyImitationRule findFirstByDeviceTypeAndProperty(DeviceType deviceType, String property);

    @Query("select d.property from DevicePropertyImitationRule d where d.deviceType = :deviceType")
    List<String> findPropertyNamesOfDeviceType(DeviceType deviceType);
}
