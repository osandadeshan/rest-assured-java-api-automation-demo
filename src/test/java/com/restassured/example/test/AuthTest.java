package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.restassured.example.HttpMethod.POST;
import static com.restassured.example.constant.ApplicationConstant.AUTH_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.AUTH_SERVICE_ENDPOINT;
import static com.restassured.example.constant.AuthenticationConstant.EMAIL;
import static com.restassured.example.constant.AuthenticationConstant.PASSWORD;
import static com.restassured.example.test.constant.TestCategory.AUTHENTICATION;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTest extends BaseTest {
    @Category(AUTHENTICATION)
    @Test(description = "Verify that a user can get the access token")
    public void testAuthentication() {
        JSONObject authRequestJson = new JSONObject();
        authRequestJson.put("email", EMAIL);
        authRequestJson.put("password", PASSWORD);

        new RestClient(AUTH_SERVICE_BASE_URL, AUTH_SERVICE_ENDPOINT, authRequestJson.toString())
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("email", equalTo(EMAIL))
                .body("status", equalTo("success"))
                .body("token", notNullValue());
    }
}
