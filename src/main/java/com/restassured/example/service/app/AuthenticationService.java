package com.restassured.example.service.app;

import com.restassured.example.util.RestClient;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;

import static com.restassured.example.HttpMethod.POST;
import static com.restassured.example.constant.ApplicationConstant.*;
import static com.restassured.example.constant.AuthenticationConstant.EMAIL;
import static com.restassured.example.constant.AuthenticationConstant.PASSWORD;

public class AuthenticationService {
    public static Map<String, String> getAuthenticationHeaders() {
        JSONObject authRequestJson = new JSONObject();
        authRequestJson.put("email", EMAIL);
        authRequestJson.put("password", PASSWORD);

        String token = new RestClient(AUTH_SERVICE_BASE_URL, AUTH_SERVICE_ENDPOINT, authRequestJson.toString())
                .sendRequest(POST)
                .extract()
                .body()
                .jsonPath()
                .get("token")
                .toString();

        return Collections.singletonMap(AUTH_HEADER_NAME, AUTH_TOKEN_PREFIX + token);
    }
}
