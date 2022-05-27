package com.microservices.datageneratorservice.model.enums;

public enum DeviceMetricsType {
    // volt
    VOLTAGE_USAGE("voltage"),
    // ampere
    INTENSITY("intensity"),
    // kilowatt
    POWER_USAGE("power_usage"),
    // ml
    WATER_USAGE("water_usage"),
    // m2
    CLEANED_AREA("cleaned_area"),
    // min
    WORKING_TIME("working_time");

    private String name;

    DeviceMetricsType(String name) {
        this.name = name;
    }
}
