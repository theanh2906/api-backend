package com.backend.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

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
    public static String buildUriWithParams(String uri, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder(uri);
        if (params != null && !params.isEmpty()) {
            builder.append("?");
            params.forEach((key, value) -> builder.append("&")
                    .append(key)
                    .append("=")
                    .append(value));
            return builder.toString();
        }
        return null;
    }
}
