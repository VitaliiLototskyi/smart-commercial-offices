package com.microservices.datageneratorservice.controller;

import com.microservices.datageneratorservice.model.Message;
import com.microservices.datageneratorservice.services.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Value(value = "${spring.kafka.template.default-topic}")
    private String topic;
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private ProducerService producerService;

    // Publish messages using the GetMapping
    @GetMapping("/publish/{count}")
    public String publishMessage(@PathVariable("count") final int count) {
        logger.info("Total number of records to send :" + count);
        for (int i = 1; i <= count; i++) {
            producerService.produce(topic);
            logger.info("Send record number: " + i);
        }

        return "Published Successfully";
    }
}
