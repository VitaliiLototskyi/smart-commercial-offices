package com.microservices.dataaggregationservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.HashMap;

public final class JSONUtil {

    private JSONUtil() {
    }

    public static DBObject jsonStrToDBObject(String obj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> map = mapper.readValue(obj, typeRef);

        return new BasicDBObject(map);
    }
}
