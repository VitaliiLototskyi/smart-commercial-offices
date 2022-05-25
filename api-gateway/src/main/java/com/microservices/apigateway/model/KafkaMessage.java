package com.microservices.apigateway.model;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class KafkaMessage {
    private String sendDate;
    private Device device;
    private static final DateTimeFormatter patternForQuery = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    public static KafkaMessage createMessage(Device device) {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setDevice(device);
        kafkaMessage.setSendDate(getFormattedToDate());

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

    public static String getFormattedToDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        return ldt.format(patternForQuery);
    }
}
