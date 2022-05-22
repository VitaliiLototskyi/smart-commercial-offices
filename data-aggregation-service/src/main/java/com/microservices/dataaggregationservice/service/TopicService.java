package com.microservices.dataaggregationservice.service;


import com.microservices.dataaggregationservice.model.Topic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private List<Topic> topics;


    public TopicService(@Value("${spring.kafka.topics}")String topicsList) {
        this.topics = Arrays
                .stream(topicsList
                        .split(","))
                .map(Topic::new)
                .collect(Collectors.toList());
    }


    public List<String> getTopics() {
        return topics.stream()
                .filter(Topic::isEnabled)
                .map(Topic::getName)
                .collect(Collectors.toList());
    }

    public void changeTopicState(String topic) {
        // TODO: change state
    }


}
