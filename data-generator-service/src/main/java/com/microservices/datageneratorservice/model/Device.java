package com.microservices.datageneratorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.datageneratorservice.model.enums.DeviceConnectionType;
import com.microservices.datageneratorservice.model.enums.DeviceState;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uuid;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_office")
    private Office office;

    private DeviceState deviceState;

    private DeviceType deviceType;

    private LocalDateTime dateTime;

    private String ipAddress;

    private Integer batteryLevel;

    private Long workingTimeMin;

    private String modelName;

    private DeviceConnectionType connectionType;

    private Integer signalStrength;
}
