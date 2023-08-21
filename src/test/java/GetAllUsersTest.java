import org.testng.annotations.Test;
import util.RestClient;

import java.util.Collections;

import static common.HttpMethod.GET;
import static constant.ApplicationConstants.GO_REST_SERVICE_BASE_URL;
import static constant.ApplicationConstants.USER_SERVICE_ENDPOINT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 18/8/23
 * Time            : 12:39 pm
 * Description     :
 **/

public class GetAllUsersTest extends BaseTest {
    @Test
    public void testGetAllUsersWithAuthHeader() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .body("meta.pagination.total", greaterThan(0));
    }

    @Test
    public void testGetAllUsersWithoutAuthHeader() {
        new RestClient(GO_REST_SERVICE_BASE_URL, USER_SERVICE_ENDPOINT, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(GET)
                .statusCode(SC_OK)
                .body("meta.pagination.total", greaterThan(0));
    }
}
