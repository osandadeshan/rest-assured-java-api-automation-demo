package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.POST;
import static com.restassured.example.constant.ApplicationConstant.GO_REST_SERVICE_BASE_URL;
import static com.restassured.example.constant.ApplicationConstant.USER_SERVICE_ENDPOINT;
import static com.restassured.example.test.constant.Gender.MALE;
import static com.restassured.example.test.constant.Status.ACTIVE;
import static com.restassured.example.test.constant.TestCategory.USER;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends BaseTest {
    private final JSONObject createUserRequestJson = new JSONObject();

    private final String email = getMockService().emails().get();
    private final String name = getMockService().names().get();
    private final String gender = MALE.asString();
    private final String status = ACTIVE.asString();

    @BeforeClass
    public void setRequestJson() {
        createUserRequestJson.put("email", email);
        createUserRequestJson.put("name", name);
        createUserRequestJson.put("gender", gender);
        createUserRequestJson.put("status", status);
    }

    @Category(USER)
    @Test(description = "Verify that a new user can be created")
    public void testUserCreation() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), createUserRequestJson.toString())
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("data.email", equalTo(email))
                .body("data.name", equalTo(name))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status))
                .body("data.id", notNullValue());
    }

    @Category(USER)
    @Test(description = "Verify that a new user cannot be created without the required fields")
    public void testUserCreationWithoutRequiredFields() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("code", equalTo(422))
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", equalTo("can't be blank"))
                .body("data[1].field", equalTo("name"))
                .body("data[1].message", equalTo("can't be blank"))
                .body("data[2].field", equalTo("gender"))
                .body("data[2].message", equalTo("can't be blank, can be male of female"))
                .body("data[3].field", equalTo("status"))
                .body("data[3].message", equalTo("can't be blank"));
    }

    @Category(USER)
    @Test(description = "Verify that a new user cannot be created without the authentication")
    public void testUserCreationWithoutAuthentication() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                false, Collections.emptyMap(), createUserRequestJson.toString())
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("code", equalTo(401))
                .body("data.message", equalTo("Authentication failed"));
    }
}
