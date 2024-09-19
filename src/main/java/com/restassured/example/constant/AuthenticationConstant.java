package com.restassured.example.constant;

import static com.restassured.example.util.FileReader.getEnvironmentConfig;

public class AuthenticationConstant {
    public static final String USERNAME = getEnvironmentConfig("username");
    public static final String PASSWORD = getEnvironmentConfig("password");
}
