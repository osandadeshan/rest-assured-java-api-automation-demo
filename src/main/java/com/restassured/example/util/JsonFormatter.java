package com.restassured.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonFormatter {
    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public static String formatJson(String jsonString) {
        String formattedString;

        try {
            jsonString = jsonString.trim();

            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                Object json = objectMapper.readValue(jsonString, Object.class);
                formattedString = prettyWriter.writeValueAsString(json);
            } else {
                formattedString = jsonString;
                logger.warn("Invalid JSON format.");
            }
        } catch (JsonProcessingException e) {
            logger.warn("JSON String couldn't format due to {}", e.getMessage());
            formattedString = jsonString;
        }

        return formattedString;
    }
}
