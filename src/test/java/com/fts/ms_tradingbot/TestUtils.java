package com.fts.ms_tradingbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T convertStringToObject(String json, Class<T> target) throws IOException {
        return objectMapper.readValue(json, target);
    }

}
