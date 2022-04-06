package com.backend.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static <T> T map(Class<T> clazz, String object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(object, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
