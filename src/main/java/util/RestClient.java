package util;

import common.HttpMethod;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static service.AuthenticationService.getAuthenticationHeaders;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/8/23
 * Time            : 4:36 pm
 * Description     :
 **/

public class RestClient {
    private final RequestSpecification requestSpecification;

    public RestClient(String baseUri, String basePath, String requestBody) {
        requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                .setBody(requestBody)
                .build();
    }

    public RestClient(String baseUri, String basePath, Map<String, ?> queryParametersMap, String requestBody) {
        requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                .addQueryParams(queryParametersMap)
                .setBody(requestBody)
                .build();
    }

    public RestClient(String baseUri, String basePath, Map<String, ?> queryParametersMap,
                      boolean isAuthTokenRequired, Map<String, String> headers, String requestBody) {
        Map<String, String> combinedHeaders = new HashMap<>(headers);
        combinedHeaders.putAll(getAuthenticationHeaders());

        if (isAuthTokenRequired)
            requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                    .addQueryParams(queryParametersMap)
                    .addHeaders(combinedHeaders)
                    .setBody(requestBody)
                    .build();
        else
            requestSpecification = getRequestSpecBuilder(baseUri, basePath)
                    .addQueryParams(queryParametersMap)
                    .addHeaders(headers)
                    .setBody(requestBody)
                    .build();
    }

    public ValidatableResponse sendRequest(HttpMethod httpMethod) {
        Response response;

        switch (httpMethod) {
            case GET:
                response = getRequestSpecification()
                        .get();
                break;

            case POST:
                response = getRequestSpecification()
                        .post();
                break;

            case PATCH:
                response = getRequestSpecification()
                        .patch();
                break;

            case PUT:
                response = getRequestSpecification()
                        .put();
                break;

            case DELETE:
                response = getRequestSpecification()
                        .delete();
                break;

            default:
                throw new InvalidArgumentException("'" + httpMethod + "' is not implemented");
        }

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
                .setContentType(JSON)
                .setAccept(JSON)
                .setBaseUri(baseUri)
                .setBasePath(basePath);
    }
}
