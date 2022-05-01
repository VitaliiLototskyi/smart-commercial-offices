package com.microservices.datageneratorservice.model;

import java.security.SecureRandom;

public enum DataCenter {
    KYIV("kyiv-1"),
    LVIV("lviv-hq"),
    IVANOFR("ifr-1"),
    CHERNIVTSI("che-1");

    private final String name;

    DataCenter(String name) {
        this.name = name;
    }

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomValueFromDataCenterTypeEnum() {
        DataCenter[] dataCenters = DataCenter.values();

        return dataCenters[random.nextInt(dataCenters.length)].name;
    }
}
