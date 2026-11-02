package com.rankup.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T map(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }
}