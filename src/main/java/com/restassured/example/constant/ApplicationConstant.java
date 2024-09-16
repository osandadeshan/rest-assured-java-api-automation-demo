package com.restassured.example.constant;

import static com.restassured.example.util.FileReader.getEnvironmentConfig;

public class ApplicationConstant {
    // Base URLs
    public static final String AUTH_SERVICE_BASE_URL = getEnvironmentConfig("auth_service_base_url");
    public static final String GO_REST_SERVICE_BASE_URL = getEnvironmentConfig("go_rest_service_base_url");

    // API Endpoints
    public static final String AUTH_SERVICE_ENDPOINT = "/auth";
    public static final String USER_SERVICE_ENDPOINT = "/public-api/users";

    // Headers
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
}
