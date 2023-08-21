import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.RestClient;

import java.util.Collections;

import static common.HttpMethod.PATCH;
import static common.HttpMethod.PUT;
import static constant.ApplicationConstants.GO_REST_SERVICE_BASE_URL;
import static constant.ApplicationConstants.USER_SERVICE_ENDPOINT;
import static constant.Gender.FEMALE;
import static constant.Status.INACTIVE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static service.UserService.getUserIdFromUserDb;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 14/8/23
 * Time            : 12:11 pm
 * Description     :
 **/

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

    @Test(description = "Verify that a new user cannot be updated without the authentication")
    public void testUserUpdateWithoutAuthentication() {
        new RestClient(GO_REST_SERVICE_BASE_URL, updateUserEndpoint, Collections.emptyMap(),
                false, Collections.emptyMap(), updateUserRequestJson.toString())
                .sendRequest(PUT)
                .statusCode(SC_OK)
                .body("code", equalTo(401))
                .body("data.message", equalTo("Authentication failed"));
    }

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
