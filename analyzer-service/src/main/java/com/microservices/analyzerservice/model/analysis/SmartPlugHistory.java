package com.microservices.analyzerservice.model.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("SMART_PLUG")
public class SmartPlugHistory extends DeviceHistory {

    private Double power;

    private Double chipTemperature;

    private boolean ledLight;
}
