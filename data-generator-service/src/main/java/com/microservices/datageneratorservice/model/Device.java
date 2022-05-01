package com.microservices.datageneratorservice.model;

import java.util.Map;

public class Device {
    private String uuid;
    private Map<DeviceMetricsType, String> deviceMetrics;
    private String dataCenter;
    private String deviceState;
    private String deviceType;
    private String dateTime;
    private String deviceIP;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<DeviceMetricsType, String> getDeviceMetrics() {
        return deviceMetrics;
    }

    public void setDeviceMetrics(Map<DeviceMetricsType, String> deviceMetrics) {
        this.deviceMetrics = deviceMetrics;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
}
