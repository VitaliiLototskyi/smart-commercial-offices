package com.microservices.datageneratorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.datageneratorservice.model.enums.DeviceConnectionType;
import com.microservices.datageneratorservice.model.enums.DeviceType;
import com.microservices.datageneratorservice.utils.MapToJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Map;

@SuppressWarnings("JpaAttributeTypeInspection")
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

    private String name;

    private DeviceType deviceType;

    private LocalDateTime updateDateTime;

    private String modelName;

    private DeviceConnectionType connectionType;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> propertyValueMap;

    @JsonIgnore
    @ColumnDefault("false")
    private boolean generationStopped;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_office")
    private Office office;
}
