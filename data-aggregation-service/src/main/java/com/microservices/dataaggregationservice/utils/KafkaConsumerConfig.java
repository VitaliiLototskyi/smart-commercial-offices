package com.microservices.dataaggregationservice.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Properties;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private static String bootstrapServer;


    public static KafkaConsumer<String, String> createConsumer() {
        final Properties confProps = new Properties();
        confProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        confProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        confProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);


        return new KafkaConsumer<>(confProps);
    }
}
