package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.GET;
import static com.restassured.example.constant.ApplicationConstant.GO_REST_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.USER_SERVICE_ENDPOINT;
import static com.restassured.example.test.constant.TestCategory.USER;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.greaterThan;

public class GetAllUsersTest extends BaseTest {
    @Category(USER)
    @Test
    public void testGetAllUsersWithAuthHeader() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .body("meta.pagination.total", greaterThan(0));
    }

    @Category(USER)
    @Test
    public void testGetAllUsersWithoutAuthHeader() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .body("meta.pagination.total", greaterThan(0));
    }
}
