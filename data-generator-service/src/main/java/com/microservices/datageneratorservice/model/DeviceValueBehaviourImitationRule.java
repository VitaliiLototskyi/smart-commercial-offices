package com.microservices.datageneratorservice.model;

import com.microservices.datageneratorservice.model.enums.DeviceType;
import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class DeviceValueBehaviourImitationRule {

    @Id
    @GeneratedValue
    private Long id;

    private DeviceType deviceType;

    private String valueName;

    private Double min;

    private Double  max;

    private Double changeStep;

    private Integer changeStepDelta;

    private Long changeDurationMs;

    private Long changeDurationDeltaMs;

    private DeviceValueChangeType  changeTypeOne;

    private DeviceValueChangeType  changeTypeTwo;

    private Long  typeChangeDurationMs;
}
