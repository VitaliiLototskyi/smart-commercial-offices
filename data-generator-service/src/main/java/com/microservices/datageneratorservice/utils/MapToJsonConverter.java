package com.microservices.datageneratorservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Converter(autoApply = true)
public class MapToJsonConverter implements AttributeConverter<Map<String, String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            ExceptionUtils.rethrow(ex);
            return "error";
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String jsonFromDB) {
        try {
            return objectMapper.readValue(jsonFromDB, Map.class);
        } catch (IOException ex) {
            ExceptionUtils.rethrow(ex);
            return new LinkedHashMap<>();
        }
    }
}