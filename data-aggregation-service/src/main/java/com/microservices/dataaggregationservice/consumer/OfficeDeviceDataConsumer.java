package com.microservices.dataaggregationservice.consumer;

import com.microservices.dataaggregationservice.service.OfficeDeviceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OfficeDeviceDataConsumer {

    private final OfficeDeviceDataService service;

    @KafkaListener(topicPattern = "office.*.device.data")
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        this.service.process(consumerRecord);
    }
}
