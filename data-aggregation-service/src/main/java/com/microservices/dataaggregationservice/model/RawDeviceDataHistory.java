package com.microservices.dataaggregationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document("officeDeviceData")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawDeviceDataHistory {

    @Id
    private String id;

    private String uuid;

    private String deviceType;

    private String name;

    private String modelName;

    private String officeName;

    private String connectionType;

    private Map<String, String> propertyValueMap;

    private LocalDateTime updateDateTime;

    private LocalDateTime dateReceived;
}
