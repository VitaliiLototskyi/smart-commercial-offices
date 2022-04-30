package com.microservices.datageneratorservice.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Message {
    private String deviceType;
    private double measurements;
    private Date measureTime;
    private Date sendDate;

    public Message() {
    }
    public Message( String deviceType, double measurements, Date measureTime, Date sendDate) {
        this.deviceType = deviceType;
        this.measurements = measurements;
        this.measureTime = measureTime;
        this.sendDate = sendDate;
    }

    public static Message generateMessage() {
        /* TO DO adequate msg */
        return new Message(DeviceType.getRandomValueFromDeviceTypeEnum(), Math.random()*1000, new Date(), new Date());
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public double getMeasurements() {
        return measurements;
    }

    public void setMeasurements(double measurements) {
        this.measurements = measurements;
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
