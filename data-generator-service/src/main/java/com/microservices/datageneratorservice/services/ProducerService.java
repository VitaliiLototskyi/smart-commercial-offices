package com.microservices.datageneratorservice.services;

import com.microservices.datageneratorservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public void produce(String topic) {
        kafkaTemplate.send(topic, Message.generateMessage());
    }
}
