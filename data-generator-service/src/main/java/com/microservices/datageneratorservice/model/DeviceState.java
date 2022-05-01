package com.microservices.datageneratorservice.model;

import java.security.SecureRandom;

public enum DeviceState {
    ON("on"),
    OFF("off");

    private final String name;

    DeviceState(String name) {
        this.name = name;
    }

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomValueFromDataStateTypeEnum() {
        DeviceState[] dataState = DeviceState.values();

        return dataState[random.nextInt(dataState.length)].name;
    }
}
