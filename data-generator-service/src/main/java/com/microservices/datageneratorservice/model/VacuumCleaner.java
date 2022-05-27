package com.microservices.datageneratorservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class VacuumCleaner {

    @Id
    @GeneratedValue
    private Long recordId;

    private Double cleanedArea;

    private Integer waterLevel;

    private Integer fanLevel;

    private boolean mopInstalled;

    private boolean isCharging;

    private Integer waterTankUsagePct;

    private Integer garbageTankUsagePct;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_uuid")
    private Device device;
}
