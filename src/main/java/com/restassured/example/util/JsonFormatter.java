package com.restassured.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFormatter {
    private final static Logger logger = LogManager.getLogger();

    public static String formatJson(String jsonString) {
        String formattedString;

        try {
            jsonString = jsonString.trim();

            if (jsonString.startsWith("{")) {
                formattedString = new JSONObject(jsonString).toString(4);
            } else if (jsonString.startsWith("[")) {
                formattedString = new JSONArray(jsonString).toString(4);
            } else {
                formattedString = jsonString;
                logger.warn("Invalid JSON format.");
            }
        } catch (JSONException e) {
            logger.warn("JSON String couldn't format due to {}", e.getMessage());
            formattedString = jsonString;
        }

        return formattedString;
    }
}
