package com.restassured.example.test.constant;

public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String asString() {
        return status;
    }
}
