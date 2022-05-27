package com.microservices.datageneratorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SmartPlug  {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long recordId;

    private Double power;

    private Integer ledLight;

    private Double chipTemperature;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_uuid")
    private Device device;
}
