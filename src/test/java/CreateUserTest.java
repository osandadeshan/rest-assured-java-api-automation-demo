import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.RestClient;

import java.util.Collections;

import static common.HttpMethod.POST;
import static constant.ApplicationConstants.GO_REST_SERVICE_BASE_URL;
import static constant.ApplicationConstants.USER_SERVICE_ENDPOINT;
import static constant.Gender.MALE;
import static constant.Status.ACTIVE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 14/8/23
 * Time            : 12:11 pm
 * Description     :
 **/

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
