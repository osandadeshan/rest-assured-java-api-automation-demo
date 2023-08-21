package service;

import com.google.common.collect.ImmutableMap;
import util.RestClient;

import java.util.Map;

import static common.HttpMethod.GET;
import static constant.ApplicationConstants.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 11/8/23
 * Time            : 2:11 pm
 * Description     :
 **/

public class AuthenticationService {
    public static Map<String, String> getAuthenticationHeaders() {
        return ImmutableMap.of(AUTH_HEADER_NAME, AUTH_TOKEN_PREFIX +
                new RestClient(AUTH_SERVICE_BASE_URL, AUTH_SERVICE_ENDPOINT, EMPTY)
                        .sendRequest(GET)
                        .extract()
                        .body()
                        .jsonPath()
                        .get("token")
                        .toString()
        );
    }
}
