package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.DELETE;
import static com.restassured.example.constant.ApplicationConstant.GO_REST_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.USER_SERVICE_ENDPOINT;
import static com.restassured.example.service.app.UserService.getUserIdFromUserDb;
import static com.restassured.example.test.constant.TestCategory.USER;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUserTest extends BaseTest {
    private String deleteUserEndpoint;

    @BeforeMethod
    public void setDeleteUserEndpoint() {
        deleteUserEndpoint = USER_SERVICE_ENDPOINT
                .concat("/")
                .concat(getUserIdFromUserDb());
    }

    @Category(USER)
    @Test(description = "Verify that a user can be deleted")
    public void testUserDeletion() {
        new RestClient(GO_REST_SERVICE_BASE_URL, deleteUserEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_OK)
                .body("code", equalTo(204));
    }

    @Category(USER)
    @Test(description = "Verify that a user cannot be deleted without the authentication")
    public void testUserDeletionWithoutAuthentication() {
        new RestClient(GO_REST_SERVICE_BASE_URL, deleteUserEndpoint, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_OK)
                .body("code", equalTo(401))
                .body("data.message", equalTo("Authentication failed"));
    }
}
