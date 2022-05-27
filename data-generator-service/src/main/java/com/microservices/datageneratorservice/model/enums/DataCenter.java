package com.microservices.datageneratorservice.model.enums;

import java.security.SecureRandom;

public enum DataCenter {
    KYIV("kyiv", "15.229.85.165"),
    LVIV("lviv", "141.136.229.46"),
    IVANOFR("ifr", "69.106.241.142"),
    CHERNIVTSI("che", "74.89.245.5");

    public final String name;
    public final String centerIP;

    DataCenter(String name, String centerIP) {
        this.name = name;
        this.centerIP = centerIP;
    }

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomValueFromDataCenterTypeEnum() {
        DataCenter[] dataCenters = DataCenter.values();

        return dataCenters[random.nextInt(dataCenters.length)].name;
    }

    public static DataCenter getDataCenterName(String dataCenterInString) {
        switch (dataCenterInString) {
            case "kyiv":
                return DataCenter.KYIV;
            case "lviv":
                return DataCenter.LVIV;
            case "ifr":
                return DataCenter.IVANOFR;
            case "che":
                return DataCenter.CHERNIVTSI;
            default:
                return null;
        }
    }
}
