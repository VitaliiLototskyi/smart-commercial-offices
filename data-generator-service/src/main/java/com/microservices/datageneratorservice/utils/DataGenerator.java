package com.microservices.datageneratorservice.utils;

import com.microservices.datageneratorservice.model.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private static final DateTimeFormatter patternForQuery = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    private static final int MAX_NUMBER_FOR_IP = 256;
    private static final int MAX_VALUE = 100000;
    private static SecureRandom secureRandom;

    public static String generateIP() {
        secureRandom = new SecureRandom();
        return secureRandom.nextInt(MAX_NUMBER_FOR_IP) + "." + secureRandom.nextInt(MAX_NUMBER_FOR_IP) + "."
                + secureRandom.nextInt(MAX_NUMBER_FOR_IP) + "." + secureRandom.nextInt(MAX_NUMBER_FOR_IP);
    }

    public static String generateUUID() {

        return UUID.randomUUID().toString();
    }

    public static Map<DeviceMetricsType, String> generateMetricsPerDeviceState(DeviceState deviceState) {
        Map<DeviceMetricsType, String> deviceMetrics = new HashMap<>();
        secureRandom = new SecureRandom();
        switch (deviceState) {
            case ON:
                deviceMetrics.put(DeviceMetricsType.POWER_USAGE, twoNumbersToString(secureRandom.nextInt(10), secureRandom.nextInt(MAX_VALUE)));
                deviceMetrics.put(DeviceMetricsType.VOLTAGE_USAGE, twoNumbersToString(220 - secureRandom.nextInt(20), secureRandom.nextInt(MAX_VALUE)));
                deviceMetrics.put(DeviceMetricsType.INTENSITY, twoNumbersToString(secureRandom.nextInt(20), secureRandom.nextInt(MAX_VALUE)));
                break;
            case OFF:
                deviceMetrics.put(DeviceMetricsType.POWER_USAGE, twoNumbersToString(0, 0));
                deviceMetrics.put(DeviceMetricsType.VOLTAGE_USAGE, twoNumbersToString(0, 0));
                deviceMetrics.put(DeviceMetricsType.INTENSITY, twoNumbersToString(0, 0));
                break;
        }

        return deviceMetrics;
    }

    public static String twoNumbersToString(Integer numberOne, Integer numberTwo) {
        return numberOne.toString() + "." + numberTwo.toString();
    }

    public static Device generateDeviceForDeviceType(DeviceType deviceType) {
        secureRandom = new SecureRandom();
        Device device = new Device();
        device.setUuid(generateUUID());

        device.setDataCenter(DataCenter.getRandomValueFromDataCenterTypeEnum());

        device.setDeviceType(DeviceType.getRandomValueFromDeviceTypeEnum());
        device.setDateTime(getFormattedToDate());
        device.setDeviceIP(generateIP());
        if (secureRandom.nextInt(1000) % 400 == 0) {
            device.setDeviceState(DeviceState.OFF.name());
            device.setDeviceMetrics(generateMetricsPerDeviceState(DeviceState.OFF));
        } else {
            device.setDeviceState(DeviceState.ON.name());
            device.setDeviceMetrics(generateMetricsPerDeviceState(DeviceState.ON));
        }

        return device;
    }

    public static String getFormattedToDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        return ldt.format(patternForQuery);
    }

    public static List<Device> generateDevices(DataCenter dataCenter) {
        secureRandom = new SecureRandom();
        List<Device> deviceList = new ArrayList<>();
        for (DeviceType deviceType : DeviceType.values()) {
                Device device = new Device();
                device.setUuid(deviceType.uuid);

                device.setDataCenter(dataCenter.name);

                device.setDeviceType(deviceType.name);
                device.setDateTime(getFormattedToDate());
                device.setDeviceIP(dataCenter.centerIP);
                if (secureRandom.nextInt(1000) % 400 == 0) {
                    device.setDeviceState(DeviceState.OFF.name());
                    device.setDeviceMetrics(generateMetricsPerDeviceState(DeviceState.OFF));
                } else {
                    device.setDeviceState(DeviceState.ON.name());
                    device.setDeviceMetrics(generateMetricsPerDeviceState(DeviceState.ON));
                }
                deviceList.add(device);
            }

        return deviceList;
    }
}
