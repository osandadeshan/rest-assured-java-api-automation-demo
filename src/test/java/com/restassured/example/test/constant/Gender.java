package com.restassured.example.test.constant;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String asString() {
        return gender;
    }
}
