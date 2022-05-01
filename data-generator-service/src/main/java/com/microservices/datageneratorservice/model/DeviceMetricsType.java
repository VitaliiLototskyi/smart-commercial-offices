package com.microservices.datageneratorservice.model;

public enum DeviceMetricsType {
    // volt
    VOLTAGE_USAGE("voltage"),
    // ampere
    INTENSITY("intensity"),
    // kilowatt
    POWER_USAGE("power_usage"),
    // ml
    WATER_USAGE("water_usage");

    private String name;

    DeviceMetricsType(String name) {
        this.name = name;
    }
}
