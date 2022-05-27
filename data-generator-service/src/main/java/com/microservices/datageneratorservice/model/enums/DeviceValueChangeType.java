package com.microservices.datageneratorservice.model.enums;

public enum DeviceValueChangeType {

    INCREASE("INCREASE"),
    DECREASE("DECREASE"),
    IDLE("IDLE"),
    NONE("NONE");

    DeviceValueChangeType(String name) {
        this.name = name;
    }

    private final String name;
}
