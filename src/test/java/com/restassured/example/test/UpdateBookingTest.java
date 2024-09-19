package com.restassured.example.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.example.Category;
import com.restassured.example.model.BookingDates;
import com.restassured.example.model.BookingRequest;
import com.restassured.example.util.RestClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.PUT;
import static com.restassured.example.constant.ApplicationConstant.BOOKING_SERVICE_ENDPOINT;
import static com.restassured.example.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.example.service.app.BookingService.getBookingIdFromBookingDb;
import static com.restassured.example.test.constant.TestCategory.BOOKING;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBookingTest extends BaseTest {
    private final BookingDates bookingDates = new BookingDates();
    private final BookingRequest bookingRequest = new BookingRequest();
    private String updateBookingEndpoint;
    private String updateBookingRequestJson;

    @BeforeMethod
    public void setUpdateBookingPayload() {
        updateBookingEndpoint = BOOKING_SERVICE_ENDPOINT
                .concat("/")
                .concat(getBookingIdFromBookingDb());

        bookingDates.setCheckIn("2024-05-01");
        bookingDates.setCheckOut("2024-05-18");

        bookingRequest.setFirstName(getMockService().names().first().get());
        bookingRequest.setLastName(getMockService().names().last().get());
        bookingRequest.setTotalPrice(156);
        bookingRequest.setDepositPaid(false);
        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setAdditionalNeeds("Lunch");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            updateBookingRequestJson = objectMapper.writeValueAsString(bookingRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking can be updated")
    public void testUpdateBooking() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, updateBookingEndpoint, Collections.emptyMap(),
                true, Collections.emptyMap(), updateBookingRequestJson)
                .sendRequest(PUT)
                .statusCode(SC_OK)
                .body("firstname", equalTo(bookingRequest.getFirstName()))
                .body("lastname", equalTo(bookingRequest.getLastName()))
                .body("totalprice", equalTo(bookingRequest.getTotalPrice()))
                .body("depositpaid", equalTo(bookingRequest.isDepositPaid()))
                .body("bookingdates.checkin", equalTo(bookingDates.getCheckIn()))
                .body("bookingdates.checkout", equalTo(bookingDates.getCheckOut()))
                .body("additionalneeds", equalTo(bookingRequest.getAdditionalNeeds()));
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be updated without the authentication")
    public void testUpdateBookingWithoutAuthentication() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, updateBookingEndpoint, Collections.emptyMap(),
                false, Collections.emptyMap(), updateBookingRequestJson)
                .sendRequest(PUT)
                .statusCode(SC_FORBIDDEN);
    }

    @Category(BOOKING)
    @Test(description = "Verify that a booking cannot be updated without the bookingId")
    public void testUpdateBookingWithoutRequiredParameters() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), updateBookingRequestJson)
                .sendRequest(PUT)
                .statusCode(SC_NOT_FOUND);
    }
}
