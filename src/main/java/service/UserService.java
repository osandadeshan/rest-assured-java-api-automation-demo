package service;

import util.RestClient;

import static common.HttpMethod.GET;
import static constant.ApplicationConstants.GO_REST_SERVICE_BASE_URL;
import static constant.ApplicationConstants.USER_SERVICE_ENDPOINT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 17/8/23
 * Time            : 6:10 pm
 * Description     :
 **/

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
