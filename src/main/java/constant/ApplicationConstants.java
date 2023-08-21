package constant;

import static util.Reader.getEnvironmentConfig;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/8/23
 * Time            : 6:14 pm
 * Description     :
 **/

public class ApplicationConstants {
    // Base URLs
    public static final String AUTH_SERVICE_BASE_URL = getEnvironmentConfig("auth_service_base_url");
    public static final String GO_REST_SERVICE_BASE_URL = getEnvironmentConfig("go_rest_service_base_url");

    // API Endpoints
    public static final String AUTH_SERVICE_ENDPOINT = getEnvironmentConfig("auth_service_endpoint");
    public static final String USER_SERVICE_ENDPOINT = getEnvironmentConfig("user_service_endpoint");

    // Headers
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
}
