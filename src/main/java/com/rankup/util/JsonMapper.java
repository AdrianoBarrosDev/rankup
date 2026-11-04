package com.rankup.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rankup.dto.CadastroUsuarioDto;

import java.util.List;

public class JsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T map(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    public static String toJson(Object objeto) {
        try {
            return mapper.writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}