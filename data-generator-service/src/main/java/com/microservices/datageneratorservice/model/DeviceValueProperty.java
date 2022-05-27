package com.microservices.datageneratorservice.model;

import com.microservices.datageneratorservice.model.enums.DeviceValueChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class DeviceValueProperty {

    @Id
    @GeneratedValue
    private Long id;

    private String valueName;

    private LocalDateTime nextUpdateTime;

    private LocalDateTime nextChangeTypeTime;

    private DeviceValueChangeType changeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_device")
    private Device device;
}
