package util;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 21/8/23
 * Time            : 9:27 pm
 * Description     :
 **/

public class Log4jFilter implements Filter {
    private final Logger logger = LogManager.getLogger();

    @Override
    public Response filter(FilterableRequestSpecification reqSpec,
                           FilterableResponseSpecification resSpec,
                           FilterContext filterContext) {

        Response response = filterContext.next(reqSpec, resSpec);

        logger.info("Method: {}", reqSpec.getMethod());
        logger.info("URI: {}", reqSpec.getURI());

        logger.info("Request Headers:");
        logger.info(reqSpec.getHeaders());

        if (!reqSpec.getPathParams().isEmpty()) {
            logger.info("Request Path Parameters:");
            logMap(reqSpec.getPathParams());
        }

        if (!reqSpec.getQueryParams().isEmpty()) {
            logger.info("Request Query Parameters:");
            logMap(reqSpec.getQueryParams());
        }

        if (!reqSpec.getFormParams().isEmpty()) {
            logger.info("Request Form Parameters:");
            logMap(reqSpec.getFormParams());
        }

        if (reqSpec.getBody() != null) {
            logger.info("Request Body:");
            logger.info(reqSpec.getBody().toString());
        }

        logger.info("Response Status Code: {}", response.statusCode());

        logger.info("Response Headers:");
        logger.info(response.getHeaders());

        logger.info("Response Body:");
        logger.info(response.getBody().asPrettyString());

        return response;
    }

    private void logMap(Map<String, String> map) {
        map.forEach((key, value) -> logger.info("{} : {}", key, value));
    }
}
