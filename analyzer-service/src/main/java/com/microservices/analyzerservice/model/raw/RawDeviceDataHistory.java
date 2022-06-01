package com.microservices.analyzerservice.model.raw;

import com.microservices.analyzerservice.model.enums.DeviceConnectionType;
import com.microservices.analyzerservice.model.enums.DeviceType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document("officeDeviceData")
@Data
@Builder
public class RawDeviceDataHistory {

    @Id
    private String id;

    private String uuid;

    private DeviceType deviceType;

    private String name;

    private String modelName;

    private String officeName;

    private DeviceConnectionType connectionType;

    private Map<String, String> propertyValueMap;

    private LocalDateTime updateDateTime;

    private LocalDateTime dateReceived;
}
