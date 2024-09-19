package com.restassured.example.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.example.Category;
import com.restassured.example.model.BookingDates;
import com.restassured.example.model.BookingRequest;
import com.restassured.example.util.RestClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.restassured.example.HttpMethod.POST;
import static com.restassured.example.constant.ApplicationConstant.BOOKING_SERVICE_ENDPOINT;
import static com.restassured.example.constant.ApplicationConstant.RESTFUL_BOOKER_BASE_URL;
import static com.restassured.example.test.constant.TestCategory.BOOKING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookingTest extends BaseTest {
    private final BookingDates bookingDates = new BookingDates();
    private final BookingRequest bookingRequest = new BookingRequest();
    private String createBookingRequestJson;

    @BeforeClass
    public void setCreateBookingRequestJson() {
        bookingDates.setCheckIn("2024-04-01");
        bookingDates.setCheckOut("2024-04-18");

        bookingRequest.setFirstName(getMockService().names().first().get());
        bookingRequest.setLastName(getMockService().names().last().get());
        bookingRequest.setTotalPrice(146);
        bookingRequest.setDepositPaid(true);
        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setAdditionalNeeds("Breakfast");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            createBookingRequestJson = objectMapper.writeValueAsString(bookingRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Category(BOOKING)
    @Test(description = "Verify that a new booking request can be raised")
    public void testCreateBooking() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), createBookingRequestJson)
                .sendRequest(POST)
                .statusCode(SC_OK)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(bookingRequest.getFirstName()))
                .body("booking.lastname", equalTo(bookingRequest.getLastName()))
                .body("booking.totalprice", equalTo(bookingRequest.getTotalPrice()))
                .body("booking.depositpaid", equalTo(bookingRequest.isDepositPaid()))
                .body("booking.bookingdates.checkin", equalTo(bookingDates.getCheckIn()))
                .body("booking.bookingdates.checkout", equalTo(bookingDates.getCheckOut()))
                .body("booking.additionalneeds", equalTo(bookingRequest.getAdditionalNeeds()));
    }

    @Category(BOOKING)
    @Test(description = "Verify that a new booking request cannot be raised without the required fields")
    public void testCreateBookingWithoutRequiredFields() {
        new RestClient(RESTFUL_BOOKER_BASE_URL, BOOKING_SERVICE_ENDPOINT, Collections.emptyMap(),
                true, Collections.emptyMap(), EMPTY)
                .sendRequest(POST)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
