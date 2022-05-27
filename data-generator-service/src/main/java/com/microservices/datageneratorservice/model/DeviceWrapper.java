package com.microservices.datageneratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceWrapper {

    private String deviceUUID;
    private String deviceBody;

}
