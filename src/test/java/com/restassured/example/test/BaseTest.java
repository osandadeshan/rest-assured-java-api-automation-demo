package com.restassured.example.test;

import com.restassured.example.util.TestListener;
import net.andreinc.mockneat.MockNeat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static com.restassured.example.constant.CommonConstant.EXECUTION_ENV_NAME;

@Listeners(TestListener.class)
public class BaseTest {
    private final Logger logger = LogManager.getLogger();

    @BeforeSuite
    public void oneTimeSetup() {
        logger.debug("Test execution environment: {}", EXECUTION_ENV_NAME);
    }

    public MockNeat getMockService() {
        return MockNeat.threadLocal();
    }
}
