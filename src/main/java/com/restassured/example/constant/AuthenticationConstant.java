package com.restassured.example.constant;

import static com.restassured.example.util.FileReader.getEnvironmentConfig;

public class AuthenticationConstant {
    public static final String EMAIL = getEnvironmentConfig("email_address");
    public static final String PASSWORD = getEnvironmentConfig("password");
}
