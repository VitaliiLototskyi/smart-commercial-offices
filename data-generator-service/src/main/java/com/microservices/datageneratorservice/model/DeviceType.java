package com.microservices.datageneratorservice.model;

import java.security.SecureRandom;

public enum DeviceType {
    SMART_PLUG("smart_plug"),
    AC("ac"),
    LIGHT_BULB("light_bulb"),
    DETECTORS("detectors"),
    WATER_ANALYZER("water_analyzer"),
    VACUUM_CLEANER("vacuum_cleaner"),
    ROUTER("router");

    private final String name;

    DeviceType(String name) {
        this.name = name;
    }

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomValueFromDeviceTypeEnum() {
        DeviceType[] deviceTypes = DeviceType.values();

        return deviceTypes[random.nextInt(deviceTypes.length)].name;
    }
}
