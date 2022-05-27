package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.DeviceValueBehaviourImitationRule;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceValueBehaviourImitationRuleRepository extends JpaRepository<DeviceValueBehaviourImitationRule, Long> {

    List<DeviceValueBehaviourImitationRule> findAllByDeviceType(DeviceType deviceType);

}
