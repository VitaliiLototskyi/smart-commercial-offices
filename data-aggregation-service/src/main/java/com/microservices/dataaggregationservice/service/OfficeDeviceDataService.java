package com.microservices.dataaggregationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.dataaggregationservice.model.RawDeviceDataHistory;
import com.microservices.dataaggregationservice.repository.RawDeviceDataHistoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeDeviceDataService {

    public static final String DEVICE_DATA_DB_COLLECTION_NAME = "officeDeviceData";

    private final RawDeviceDataHistoryRepo repo;
    private final ObjectMapper objectMapper;

    public void process(ConsumerRecord<String, String> consumerRecord) {
        try {
            RawDeviceDataHistory rawDeviceDataHistory = this.objectMapper.readValue(consumerRecord.value(), RawDeviceDataHistory.class);
            repo.save(rawDeviceDataHistory);

            log.info("Saved device data to {}. Device UUID: {}. Device data: {}", DEVICE_DATA_DB_COLLECTION_NAME, consumerRecord.key(), consumerRecord.value());
        } catch (Exception e) {
            log.error("Something went wrong while saving device data to {}. Device UUID: {}. Device data: {}", DEVICE_DATA_DB_COLLECTION_NAME, consumerRecord.key(), consumerRecord.value(), e);
            ExceptionUtils.rethrow(e);
        }
    }
}
