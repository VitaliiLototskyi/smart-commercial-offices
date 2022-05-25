package com.microservices.apigateway.services;

import com.microservices.apigateway.model.Device;
import com.microservices.apigateway.model.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Value(value = "spring.kafka.template.kyiv")
    public String kyivOffice;
    @Value(value = "spring.kafka.template.lviv")
    public String lvivOffice;
    @Value(value = "spring.kafka.template.che")
    public String cheOffice;
    @Value(value = "spring.kafka.template.ifr")
    public String ifrOffice;

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendDataPerOffice(List<Device> deviceList) {
        for (Device device : deviceList) {
            switch (device.getDataCenter()) {
                case "kyiv":
                    kafkaTemplate.send(kyivOffice, KafkaMessage.createMessage(device));
                    break;
                case "lviv":
                    kafkaTemplate.send(lvivOffice, KafkaMessage.createMessage(device));
                    break;
                case "ifr":
                    kafkaTemplate.send(ifrOffice, KafkaMessage.createMessage(device));
                    break;
                case "che":
                    kafkaTemplate.send(cheOffice, KafkaMessage.createMessage(device));
                    break;
            }
        }
    }
}