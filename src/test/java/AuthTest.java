import org.testng.annotations.Test;
import util.RestClient;

import static common.HttpMethod.GET;
import static constant.ApplicationConstants.AUTH_SERVICE_BASE_URL;
import static constant.ApplicationConstants.AUTH_SERVICE_ENDPOINT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/8/23
 * Time            : 2:51 pm
 * Description     :
 **/

public class AuthTest extends BaseTest {
    @Test(description = "Verify that a user can get the access token")
    public void testAuthentication() {
        new RestClient(AUTH_SERVICE_BASE_URL, AUTH_SERVICE_ENDPOINT, EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .body("username", equalTo("Osanda"))
                .body("status", equalTo("success"))
                .body("token", notNullValue());
    }
}
