package com.restassured.example.test;

import com.restassured.example.Category;
import com.restassured.example.util.RestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.DELETE;
import static com.restassured.example.constant.ApplicationConstant.BOOKING_SERVICE_ENDPOINT;
import static com.restassured.example.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.example.service.app.BookingService.getBookingIdFromBookingDb;
import static com.restassured.example.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class DeleteBookingTest extends BaseTest {
    private String deleteBookingEndpoint;

    @BeforeMethod
    public void setDeleteBookingEndpoint() {
        deleteBookingEndpoint = BOOKING_SERVICE_ENDPOINT
                .concat("/")
                .concat(getBookingIdFromBookingDb());
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking can be deleted")
    public void testBookingDeletion() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, deleteBookingEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_CREATED);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be deleted without the authentication")
    public void testBookingDeletionWithoutAuthentication() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, deleteBookingEndpoint, Collections.emptyMap(),
                false, Collections.emptyMap(), EMPTY)
                .sendRequest(DELETE)
                .statusCode(SC_FORBIDDEN);
    }
}
