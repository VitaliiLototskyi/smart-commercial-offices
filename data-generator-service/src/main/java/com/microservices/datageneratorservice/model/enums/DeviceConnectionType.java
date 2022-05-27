package com.microservices.datageneratorservice.model.enums;

public enum DeviceConnectionType {

    WIFI("WIFI"),
    BLE_MESH("BLE_MESH"),
    ZIGBEE("ZIGBEE"),
    WIRED("WIRED");

    DeviceConnectionType(String name) {
        this.name = name;
    }

    private final String name;
}
