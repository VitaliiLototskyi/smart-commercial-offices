package com.microservices.dataaggregationservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.HashMap;

public final class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtil() {
    }

    public static DBObject jsonStrToDBObject(String obj) throws JsonProcessingException {
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> map = objectMapper.readValue(obj, typeRef);

        return new BasicDBObject(map);
    }

    public static String getFieldFromJson(String json, String field) throws JsonProcessingException {
        final ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
        if (!node.hasNonNull(field)) {
            throw new IllegalArgumentException("Provided json doesn't have a not null field " + field + ". JSON: " + json);
        }
        return node.get(field).asText();
    }
}
