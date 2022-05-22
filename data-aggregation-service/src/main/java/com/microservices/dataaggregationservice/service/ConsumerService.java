package com.microservices.dataaggregationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.dataaggregationservice.utils.JSONUtil;
import com.microservices.dataaggregationservice.utils.KafkaConsumerConfig;
import com.mongodb.DBObject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;


@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);


    @Autowired
    TopicService topicService;

    @Autowired
    MongoTemplate mongoTemplate;

    void consume() {

        while (true) {
            List<String> topics = topicService.getTopics();
            if (!topics.isEmpty()) {

                KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerConfig.createConsumer();
                kafkaConsumer.subscribe(topics);
                Duration time = Duration.ofSeconds(1);
                ConsumerRecords<String, String> records = kafkaConsumer.poll(time);
                for (ConsumerRecord<String, String> record : records) {
                    String topicName = record.topic();
                    try {
                        DBObject dbObject = JSONUtil.jsonStrToDBObject(record.value());
                        dbObject.put("topic", topicName);
                        mongoTemplate.save(dbObject, "devices");
                    } catch (JsonProcessingException e) {
                        logger.error(e.getMessage());
                    }

                }
                kafkaConsumer.commitAsync();

            }
        }


    }
}
