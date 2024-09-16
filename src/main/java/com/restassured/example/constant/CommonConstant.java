package com.restassured.example.constant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonConstant {
    private final static Logger logger = LogManager.getLogger();
    public static final String EXECUTION_ENV_NAME = getEnvironmentName();

    private static String getEnvironmentName() {
        String environmentNameFromPomXml = System.getProperty("environment");
        String envName;

        if (environmentNameFromPomXml != null)
            envName = environmentNameFromPomXml;
        else {
            logger.warn("The Maven Profile is missing the environment configuration.");
            logger.warn("The default environment 'dev' will be enabled for this run.");
            envName = "dev";
        }

        return envName.toLowerCase();
    }
}
