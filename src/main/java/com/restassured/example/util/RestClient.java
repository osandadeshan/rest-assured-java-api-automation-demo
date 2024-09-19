package com.restassured.example.util;

import com.restassured.example.HttpMethod;
import com.restassured.example.service.ExtentReportService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.restassured.example.service.app.AuthenticationService.getAuthenticationHeaders;
import static com.restassured.example.util.JsonFormatter.formatJson;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class RestClient {
    private final RequestSpecification requestSpecification;
    private final String fullRequestUrl;
    private final ExtentReportService extentReportService;

    public RestClient(String baseUri, String basePath, String requestBody) {
        requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                .setBody(requestBody)
                .build();

        this.fullRequestUrl = baseUri + basePath;

        this.extentReportService = ExtentReportService.getInstance();
    }

    @SuppressWarnings("unused")
    public RestClient(String baseUri, String basePath, Map<String, ?> queryParametersMap, String requestBody) {
        requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                .addQueryParams(queryParametersMap)
                .setBody(requestBody)
                .build();

        this.fullRequestUrl = queryParametersMap.isEmpty()
                ? baseUri + basePath
                : baseUri + basePath + queryParametersMap;

        this.extentReportService = ExtentReportService.getInstance();
    }

    public RestClient(String baseUri, String basePath, Map<String, ?> queryParametersMap,
                      boolean isAuthTokenRequired, Map<String, String> headers, String requestBody) {
        Map<String, String> combinedHeaders = new HashMap<>(headers);
        combinedHeaders.putAll(getAuthenticationHeaders());

        if (isAuthTokenRequired) {
            requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                    .addQueryParams(queryParametersMap)
                    .addHeaders(combinedHeaders)
                    .setBody(requestBody)
                    .build();
        } else {
            requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                    .addQueryParams(queryParametersMap)
                    .addHeaders(headers)
                    .setBody(requestBody)
                    .build();
        }

        this.fullRequestUrl = queryParametersMap.isEmpty()
                ? baseUri + basePath
                : baseUri + basePath + queryParametersMap;

        this.extentReportService = ExtentReportService.getInstance();
    }

    public ValidatableResponse sendRequest(HttpMethod httpMethod) {
        Response response;

        switch (httpMethod) {
            case GET:
                response = getRequestSpecification().get();
                break;

            case POST:
                response = getRequestSpecification().post();
                break;

            case PATCH:
                response = getRequestSpecification().patch();
                break;

            case PUT:
                response = getRequestSpecification().put();
                break;

            case DELETE:
                response = getRequestSpecification().delete();
                break;

            default:
                throw new RuntimeException("'" + httpMethod + "' is not implemented");
        }

        extentReportService.logRequestDetails(
                fullRequestUrl, httpMethod.toString(),
                headersToMap(((RequestSpecificationImpl) requestSpecification).getHeaders()),
                formatJson(((RequestSpecificationImpl) requestSpecification).getBody().toString())
        );

        extentReportService.logResponseDetails(
                response.getStatusCode(),
                formatJson(response.asString())
        );

        return response
                .then()
                .log().all();
    }

    private RequestSpecification getRequestSpecification() {
        return given()
                .filter(new Log4jFilter())
                .spec(requestSpecification)
                .log().all();
    }

    private RequestSpecBuilder getRequestSpecBuilder(String baseUri, String basePath) {
        return new RequestSpecBuilder()
//        TODO: Uncomment the following line to use your actual APIs
//                .setAccept(JSON)
                .setContentType(JSON)
                .setBaseUri(baseUri)
                .setBasePath(basePath);
    }

    private Map<String, ?> headersToMap(Headers headers) {
        return headers.asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
    }
}
