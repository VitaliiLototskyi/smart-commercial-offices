package com.microservices.datageneratorservice.model;

import com.microservices.datageneratorservice.utils.DataGenerator;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class KafkaMessage {
    private String sendDate;
    private Device device;
    private static final DateTimeFormatter patternForQuery = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    public static KafkaMessage generateMessage(DeviceType deviceType) {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setDevice(DataGenerator.generateDeviceForDeviceType(deviceType));
        kafkaMessage.setSendDate(DataGenerator.getFormattedToDate());

        return kafkaMessage;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
