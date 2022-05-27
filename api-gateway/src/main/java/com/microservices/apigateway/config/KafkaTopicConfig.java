package com.microservices.apigateway.config;

import com.microservices.apigateway.services.FileResourceService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.config.TopicBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Pulls list of authenticated  office names from text file by imitating some authentication DB
 * and creates topics per each office in the list using Kafka Admin
 */
@Configuration
public class KafkaTopicConfig {

    public static final String OFFICE_DEVICE_DATA_TOPIC_FMT = "office.%s.device.data";

    private final SingletonBeanRegistry beanRegistry;
    private final FileResourceService fileResourceService;

    public KafkaTopicConfig(GenericApplicationContext context, FileResourceService fileResourceService) {
        this.beanRegistry = context.getBeanFactory();
        this.fileResourceService = fileResourceService;
    }

    @PostConstruct
    public void createTopicsPerOffice() {
        List<String> officeNames = fileResourceService.readResourceFileLines("auth-offices.lst");
        for (String officeName : officeNames) {
            NewTopic newTopic = this.generateDefaultTopic(String.format(OFFICE_DEVICE_DATA_TOPIC_FMT, officeName));
            beanRegistry.registerSingleton("topic-" + newTopic.name(), newTopic);
        }
    }

    private NewTopic generateDefaultTopic(String name) {
        return TopicBuilder.name(name)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
