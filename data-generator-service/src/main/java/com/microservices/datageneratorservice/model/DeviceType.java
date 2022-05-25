package com.microservices.datageneratorservice.model;

import java.security.SecureRandom;

public enum DeviceType {
    SMART_PLUG("smart_plug", "a844b7f8-eda3-42a2-94e4-cd2b17cbb43c"),
    AC("ac", "200547b8-d7c0-4dae-9fd2-ad2473415333"),
    LIGHT_BULB("light_bulb", "aeede3b1-43bf-4bcc-baa2-3b943fc502bd"),
    DETECTORS("detectors", "530d7d4d-8071-40dd-b9c1-a076e6cf75f0"),
    WATER_ANALYZER("water_analyzer", "1aa781ef-1e6e-4006-88f4-a61a7d97d2ec"),
    VACUUM_CLEANER("vacuum_cleaner", "b022f0ab-e4ff-4a35-9d08-52d96b24397d"),
    ROUTER("router", "f2a63f34-ba15-4d96-9224-1093e7823e53");

    public final String name;
    public final String uuid;

    DeviceType(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomValueFromDeviceTypeEnum() {
        DeviceType[] deviceTypes = DeviceType.values();

        return deviceTypes[random.nextInt(deviceTypes.length)].name;
    }
}
