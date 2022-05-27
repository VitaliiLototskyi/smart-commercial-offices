package com.microservices.dataaggregationservice.service;

import com.microservices.dataaggregationservice.utils.JSONUtil;
import com.mongodb.DBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeDeviceDataService {

    public static final String DEVICE_DATA_DB_COLLECTION_NAME = "officeDeviceData";

    private final MongoTemplate mongoTemplate;

    public void process(ConsumerRecord<String, String> consumerRecord) {
        try {
            DBObject dbObject = JSONUtil.jsonStrToDBObject(consumerRecord.value());
            dbObject.put("uuid", UUID.randomUUID().toString());
            mongoTemplate.save(dbObject, DEVICE_DATA_DB_COLLECTION_NAME);

            log.info("Saved device data to {}. Device ID: {}. Device data: {}", DEVICE_DATA_DB_COLLECTION_NAME, consumerRecord.key(), consumerRecord.value());
        } catch (Exception e) {
            log.error("Something went wrong while saving device data to {}. Device ID: {}. Device data: {}", DEVICE_DATA_DB_COLLECTION_NAME, consumerRecord.key(), consumerRecord.value(), e);
            ExceptionUtils.rethrow(e);
        }
    }
}
