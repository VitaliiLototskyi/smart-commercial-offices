package com.microservices.datageneratorservice.model.enums;

public enum DeviceState {

    ON("ON"),
    OFF("OFF");

    DeviceState(String name) {
        this.name = name;
    }

    private final String name;
}
