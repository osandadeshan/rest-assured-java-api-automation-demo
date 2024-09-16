package com.restassured.example.service.app;

import com.restassured.example.util.RestClient;

import static com.restassured.example.HttpMethod.GET;
import static com.restassured.example.constant.ApplicationConstant.GO_REST_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.USER_SERVICE_ENDPOINT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;

public class UserService {
    public static String getUserIdFromUserDb() {
        return new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .get("data[0].id")
                .toString();
    }
}
