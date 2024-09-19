package com.restassured.example.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.example.Category;
import com.restassured.example.model.AuthenticationRequest;
import com.restassured.example.util.RestClient;
import org.testng.annotations.Test;

import static com.restassured.example.HttpMethod.POST;
import static com.restassured.example.constant.ApplicationConstant.AUTH_SERVICE_ENDPOINT;
import static com.restassured.example.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.example.constant.AuthenticationConstant.PASSWORD;
import static com.restassured.example.constant.AuthenticationConstant.USERNAME;
import static com.restassured.example.test.constant.TestCategory.AUTHENTICATION;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTest extends BaseTest {
    @Category(AUTHENTICATION)
    @Test(description = "Verify that a user can get the access token")
    public void testAuthentication() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(USERNAME);
        authenticationRequest.setPassword(PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String authRequestJson;
        try {
            authRequestJson = objectMapper.writeValueAsString(authenticationRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        new RestClient(RESTFUL_BOOKER_BASE_URL, AUTH_SERVICE_ENDPOINT, authRequestJson)
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("token", notNullValue());
    }
}
