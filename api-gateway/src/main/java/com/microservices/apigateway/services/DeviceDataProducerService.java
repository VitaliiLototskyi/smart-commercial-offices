package com.microservices.apigateway.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservices.apigateway.config.KafkaTopicConfig;
import com.microservices.apigateway.model.DeviceWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceDataProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final FileResourceService fileResourceService;
    private final ObjectMapper objectMapper;

    /**
     *  Sends device data to the topic that corresponds to the source office.
     *  Message key - device UUID
     *  Message value - device data JSON
     */
    public void sendDataPerOffice(String officeName, DeviceWrapper wrapper) {
        if (!this.isOfficeAuthenticated(officeName)) {
            return;
        }
        final String officeDeviceDataTopic = String.format(KafkaTopicConfig.OFFICE_DEVICE_DATA_TOPIC_FMT, officeName);
        String messageKey = wrapper.getDeviceUUID();
        Map<String, String> reqFieldValueMap = Map.of("officeName", officeName, "dateReceived", LocalDateTime.now().toString());
        Optional<String> messageValue = this.addRequiredFieldValuesToDeviceData(wrapper.getDeviceBody(), reqFieldValueMap);
        if (messageValue.isEmpty()) {
            return;
        }
        this.kafkaTemplate.send(officeDeviceDataTopic, messageKey, messageValue.get())
                .completable()
                .thenAccept(rt -> {
                    RecordMetadata metadata = rt.getRecordMetadata();
                    ProducerRecord<String, String> producerRec = rt.getProducerRecord();
                    log.info("Kafka. Sent device data to topic {} partition: {}. ID {}. Value: {} ", metadata.topic(), metadata.partition(), producerRec.key(), producerRec.value());
                })
                .exceptionally(ex -> {
                    log.error("Kafka. Failed to Send device data to topic {}. Device ID {}. Data: {} ", officeDeviceDataTopic, wrapper.getDeviceUUID(), wrapper.getDeviceBody(), ex);
                    return null;
                });
    }

    private Optional<String> addRequiredFieldValuesToDeviceData(String inputDataJSON, Map<String, String> reqFieldValueMap) {
        String resultJSONStr = null;
        try {
            ObjectNode actualObj = (ObjectNode) objectMapper.readTree(inputDataJSON);
            reqFieldValueMap.forEach(actualObj::put);
            resultJSONStr = this.objectMapper.writeValueAsString(actualObj);
        } catch (Exception e) {
            log.error("Something went wrong while adding required fields to device data JSON: {}", inputDataJSON, e);
        }

        return Optional.ofNullable(resultJSONStr);
    }

    /**
     * Office authentication imitation
     */
    private boolean isOfficeAuthenticated(String officeName) {
        return fileResourceService.readResourceFileLines("auth-offices.lst").stream()
                .anyMatch(x -> x.equals(officeName));
    }

}