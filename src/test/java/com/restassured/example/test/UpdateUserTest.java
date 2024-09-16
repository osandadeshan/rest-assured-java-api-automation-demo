package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.PATCH;
import static com.restassured.example.HttpMethod.PUT;
import static com.restassured.example.constant.ApplicationConstant.GO_REST_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.USER_SERVICE_ENDPOINT;
import static com.restassured.example.service.app.UserService.getUserIdFromUserDb;
import static com.restassured.example.test.constant.Gender.FEMALE;
import static com.restassured.example.test.constant.Status.INACTIVE;
import static com.restassured.example.test.constant.TestCategory.USER;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest extends BaseTest {
    private String updateUserEndpoint;
    private final JSONObject updateUserRequestJson = new JSONObject();

    private final String email = getMockService().emails().get();
    private final String name = getMockService().names().get();
    private final String gender = FEMALE.asString();
    private final String status = INACTIVE.asString();

    @BeforeMethod
    public void setUpdateUserEndpoint() {
        updateUserEndpoint = USER_SERVICE_ENDPOINT
                .concat("/")
                .concat(getUserIdFromUserDb());

        updateUserRequestJson.put("email", email);
        updateUserRequestJson.put("name", name);
        updateUserRequestJson.put("gender", gender);
        updateUserRequestJson.put("status", status);
    }

    @Category(USER)
    @Test(description = "Verify that a user can be updated")
    public void testUserUpdate() {
        new RestClient(GO_REST_SERVICE_BASE_URL, updateUserEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), updateUserRequestJson.toString())
                .sendRequest(PUT)
                .statusCode(SC_OK)
                .body("data.email", equalTo(email))
                .body("data.name", equalTo(name))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status))
                .body("data.id", notNullValue());
    }

    @Category(USER)
    @Test(description = "Verify that a new user cannot be updated without the authentication")
    public void testUserUpdateWithoutAuthentication() {
        new RestClient(GO_REST_SERVICE_BASE_URL, updateUserEndpoint, Collections.emptyMap(),
                false, Collections.emptyMap(), updateUserRequestJson.toString())
                .sendRequest(PUT)
                .statusCode(SC_OK)
                .body("code", equalTo(401))
                .body("data.message", equalTo("Authentication failed"));
    }

    @Category(USER)
    @Test(description = "Verify that a few parameters of a new user can be updated")
    public void testUserUpdateWithFewParameters() {
        updateUserRequestJson.remove("email");

        new RestClient(GO_REST_SERVICE_BASE_URL, updateUserEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), updateUserRequestJson.toString())
                .sendRequest(PATCH)
                .statusCode(SC_OK)
                .body("data.email", not(equalTo(email)))
                .body("data.name", equalTo(name))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status))
                .body("data.id", notNullValue());
    }
}
