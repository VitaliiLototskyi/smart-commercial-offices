package com.microservices.datageneratorservice.model;

import java.util.List;

public class DeviceWrapper {
    private List<Device> deviceList;

    public DeviceWrapper(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
