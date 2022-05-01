package com.microservices.datageneratorservice.services;

import com.microservices.datageneratorservice.model.DeviceType;
import com.microservices.datageneratorservice.model.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void produce(String topic) {
        for (DeviceType deviceType : DeviceType.values()) {
            kafkaTemplate.send(topic, KafkaMessage.generateMessage(deviceType));
        }
    }
}
