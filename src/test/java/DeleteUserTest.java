import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.RestClient;

import java.util.Collections;

import static common.HttpMethod.DELETE;
import static constant.ApplicationConstants.GO_REST_SERVICE_BASE_URL;
import static constant.ApplicationConstants.USER_SERVICE_ENDPOINT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static service.UserService.getUserIdFromUserDb;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 17/8/23
 * Time            : 5:51 pm
 * Description     :
 **/

public class DeleteUserTest extends BaseTest {
    private String deleteUserEndpoint;

    @BeforeMethod
    public void setDeleteUserEndpoint() {
        deleteUserEndpoint = USER_SERVICE_ENDPOINT
                .concat("/")
                .concat(getUserIdFromUserDb());
    }

    @Test(description = "Verify that a user can be deleted")
    public void testUserDeletion() {
        new RestClient(GO_REST_SERVICE_BASE_URL, deleteUserEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_OK)
                .body("code", equalTo(204));
    }

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
