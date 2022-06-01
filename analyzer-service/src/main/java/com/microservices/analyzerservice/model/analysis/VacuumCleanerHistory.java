package com.microservices.analyzerservice.model.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@DiscriminatorValue("VACUUM_CLEANER")
public class VacuumCleanerHistory extends DeviceHistory {

    private Double cleanedArea;

    private Integer waterLevel;

    private Integer fanLevel;

    private boolean mopInstalled;

    private boolean isCharging;

    private Integer waterTankUsagePct;

    private Integer garbageTankUsagePct;
}
