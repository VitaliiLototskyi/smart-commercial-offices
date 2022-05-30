package com.microservices.datageneratorservice.model;

import com.microservices.datageneratorservice.model.enums.DeviceType;
import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class DevicePropertyImitationRule {

    @Id
    @GeneratedValue
    private Long id;

    private DeviceType deviceType;

    private String property;

    @ColumnDefault("false")
    private boolean stopGenIfMin;

    private String correlatedProperty;

    private Double correlationCoef;

    private String triggerByProp;

    private Double triggerByPropVal;

    private Double setValOnTriggerByProp;

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
