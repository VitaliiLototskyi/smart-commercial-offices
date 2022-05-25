package com.microservices.apigateway.model;

import java.util.List;

public class DeviceWrapper {
    private List<Device> deviceList;

    public DeviceWrapper() {
    }

    public DeviceWrapper(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public String toString() {
        return "DeviceWrapper{" +
                "deviceList=" + deviceList +
                '}';
    }
}
