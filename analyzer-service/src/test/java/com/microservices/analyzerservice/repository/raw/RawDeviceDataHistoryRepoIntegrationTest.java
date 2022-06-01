package com.microservices.analyzerservice.repository.raw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.analyzerservice.model.raw.RawDeviceDataHistory;
import com.microservices.analyzerservice.utility.JSONUtil;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RawDeviceDataHistoryRepoIntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RawDeviceDataRepo repo;

    final List<String> deviceDataJsonList = List.of(
            "{\"connectionType\":\"WIFI\",\"dateReceived\":{\"$date\":\"2010-05-31T10:28:21.092Z\"},\"deviceType\":\"SMART_PLUG\",\"modelName\":\"smart-plug-v4\",\"name\":\"refrigerator-plug-3\",\"officeName\":\"lviv-2\",\"propertyValueMap\":{\"chipTemperature\":\"15.0\",\"ledLight\":\"1.0\",\"power\":\"0.0\",\"signalStrength\":\"60.5\",\"turnedOn\":\"0.0\",\"workingTimeMin\":\"16.0\"},\"updateDateTime\":{\"$date\":\"2022-05-31T10:28:20.958Z\"},\"uuid\":\"1e4f9bcc-94e0-4fad-95e9-0f721ebfab0c\"}",
            "{\"connectionType\":\"WIFI\",\"dateReceived\":{\"$date\":\"2012-05-31T10:28:21.092Z\"},\"deviceType\":\"SMART_PLUG\",\"modelName\":\"smart-plug-v4\",\"name\":\"refrigerator-plug-3\",\"officeName\":\"lviv-2\",\"propertyValueMap\":{\"chipTemperature\":\"15.0\",\"ledLight\":\"1.0\",\"power\":\"0.0\",\"signalStrength\":\"60.5\",\"turnedOn\":\"0.0\",\"workingTimeMin\":\"17.0\"},\"updateDateTime\":{\"$date\":\"2022-05-31T10:28:20.958Z\"},\"uuid\":\"1e4f9bcc-94e0-4fad-95e9-0f721ebfab0c\"}",
            "{\"connectionType\":\"WIFI\",\"dateReceived\":{\"$date\":\"2014-05-31T10:28:21.092Z\"},\"deviceType\":\"SMART_PLUG\",\"modelName\":\"smart-plug-v4\",\"name\":\"refrigerator-plug-3\",\"officeName\":\"lviv-2\",\"propertyValueMap\":{\"chipTemperature\":\"15.0\",\"ledLight\":\"1.0\",\"power\":\"0.0\",\"signalStrength\":\"60.5\",\"turnedOn\":\"0.0\",\"workingTimeMin\":\"18.0\"},\"updateDateTime\":{\"$date\":\"2022-05-31T10:28:20.958Z\"},\"uuid\":\"1e4f9bcc-94e0-4fad-95e9-0f721ebfab0c\"}"
    );

    @Test
    void testValuesAreSuccessfullyConvertedFromRawDataDBToSystem() throws JsonProcessingException {
        // save raw device data first
        DBObject objectToSave = JSONUtil.jsonStrToDBObject(deviceDataJsonList.get(0));
        mongoTemplate.save(objectToSave, "officeDeviceData");

        List<RawDeviceDataHistory> rawDeviceDataHistoryList = this.repo.findAll();
        assertEquals(1, rawDeviceDataHistoryList.size());
        assertEquals("1e4f9bcc-94e0-4fad-95e9-0f721ebfab0c", rawDeviceDataHistoryList.get(0).getUuid());
        Map<String, String> propertyValueMap = rawDeviceDataHistoryList.get(0).getPropertyValueMap();
        assertNotNull(propertyValueMap);
        assertEquals(6, propertyValueMap.size());
        assertEquals(16, Double.parseDouble(propertyValueMap.get("workingTimeMin")));
    }
}
