package com.microservices.analyzerservice.model.analysis;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="DEVICE_TYPE", discriminatorType = DiscriminatorType.STRING)
public class DeviceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REC_ID")
    protected Long recId;

    protected Double signalStrength;

    protected boolean turnedOn;

    protected Integer workingTimeMin;

    protected LocalDateTime dateReceived;

    protected LocalDateTime dateProcessed;

    protected LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID", nullable = false)
    protected Device device;
}
