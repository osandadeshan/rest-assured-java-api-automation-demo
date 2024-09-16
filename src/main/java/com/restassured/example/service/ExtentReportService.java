package com.restassured.example.service;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.restassured.example.constant.CommonConstant.EXECUTION_ENV_NAME;
import static com.restassured.example.constant.ReporterConstant.*;
import static com.restassured.example.util.AnnotationReader.getTestMethodCategory;

public class ExtentReportService {
    private static final ExtentReports extentReports = new ExtentReports();
    private static ExtentReportService instance;
    private final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private final static Logger logger = LogManager.getLogger();

    private ExtentReportService() {
    }

    public static ExtentReportService getInstance() {
        if (instance == null) {
            instance = new ExtentReportService();
        }
        return instance;
    }

    public void initializeExtentReporter(String timestamp) {
        ExtentSparkReporter sparkAllTestsReporter = new ExtentSparkReporter(EXTENT_FULL_REPORT_DIRECTORY
                + FILE_SEPARATOR + EXTENT_REPORT_FILE_NAME_PREFIX + timestamp + ".html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY, ViewName.EXCEPTION})
                .apply();

        extentReports.attachReporter(sparkAllTestsReporter);

        try {
            if (EXTENT_REPORT_THEME.equalsIgnoreCase("dark"))
                sparkAllTestsReporter.config().setTheme(Theme.DARK);
            else
                sparkAllTestsReporter.config().setTheme(Theme.STANDARD);

            sparkAllTestsReporter.config().setDocumentTitle(EXTENT_REPORT_DOCUMENT_TITLE);
            sparkAllTestsReporter.config().setReportName(EXTENT_REPORT_REPORTER_NAME);

            extentReports.setSystemInfo("Application Name", APPLICATION_NAME);
            extentReports.setSystemInfo("Environment", EXECUTION_ENV_NAME.toUpperCase());
            extentReports.setSystemInfo("Test Developer", TEST_DEVELOPER);

        } catch (Exception ex) {
            sparkAllTestsReporter.config().setTheme(Theme.STANDARD);
        }
    }

    public void startTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        testMap.put(Thread.currentThread().getId(), test);  // Map the test instance to the current thread
    }

    private ExtentTest getCurrentTest() {
        return testMap.get(Thread.currentThread().getId());  // Get the test instance for the current thread
    }

    public void logRequestDetails(String url, String method, Map<String, ?> headers, String requestBody) {
        ExtentTest currentTest = getCurrentTest();

        if (currentTest != null) {
            currentTest.info("<b>Request URL: </b>" + url);
            currentTest.info("<b>HTTP Method: </b>" + method);
            currentTest.info("<b>Request Headers: </b>" + headers);

            if (!requestBody.isEmpty()) {
                currentTest.info("<b>Request Body: </b><br /><pre>" + requestBody + "</pre>");
            }

        } else {
            logger.warn("Current test instance is null while logging request details!");
        }
    }

    public void logResponseDetails(int statusCode, String responseBody) {
        ExtentTest currentTest = getCurrentTest();

        if (currentTest != null) {
            currentTest.info("<b>Response Status Code: </b>" + statusCode);
            currentTest.info("<b>Response Body: </b><br /><pre>" + responseBody + "</pre>");
        } else {
            logger.warn("Current test instance is null while logging response details!");
        }
    }

    public void updateExtentReportOnTestSuccess(ITestResult iTestResult) {
        ExtentTest currentTest = getCurrentTest();
        if (currentTest != null) {
            String iTestDescription = iTestResult.getMethod().getDescription();

            currentTest.info("<b> Test Class: </b> <br />" + iTestResult.getTestClass().getName())
                    .info("<b> Test Method Name: </b> <br />" + iTestResult.getName());

            if (iTestDescription != null && !iTestDescription.isEmpty()) {
                currentTest.info("<b> Test Method Description: </b> <br />" + iTestDescription);
            }

            String category = getTestMethodCategory(iTestResult);
            if (category != null) {
                currentTest.assignCategory(category);
            }
        }
    }

    public void updateExtentReportOnTestFailure(ITestResult iTestResult) {
        ExtentTest currentTest = getCurrentTest();
        if (currentTest != null) {
            String iTestDescription = iTestResult.getMethod().getDescription();

            currentTest.info("<b> Test Class: </b> <br />" + iTestResult.getTestClass().getName())
                    .info("<b> Test Method Name: </b> <br />" + iTestResult.getName());

            if (iTestDescription != null && !iTestDescription.isEmpty()) {
                currentTest.info("<b> Test Method Description: </b> <br />" + iTestDescription);
            }

            currentTest.createNode("<b> Error Details: </b>")
                    .fail("<b> Error Message: </b> <br />" + iTestResult.getThrowable())
                    .fail(iTestResult.getThrowable());

            String category = getTestMethodCategory(iTestResult);
            if (category != null) {
                currentTest.assignCategory(category);
            }
        }
    }

    public void updateExtentReportOnTestSkipped(ITestResult iTestResult) {
        ExtentTest currentTest = getCurrentTest();
        if (currentTest != null) {
            String iTestDescription = iTestResult.getMethod().getDescription();

            currentTest.info("<b> Test Class: </b> <br />" + iTestResult.getTestClass().getName())
                    .info("<b> Test Method Name: </b> <br />" + iTestResult.getName());

            if (iTestDescription != null && !iTestDescription.isEmpty()) {
                currentTest.info("<b> Test Method Description: </b> <br />" + iTestDescription);
            }

            currentTest.createNode("<b> Error Details: </b>")
                    .skip("<b> Error Message: </b> <br />" + iTestResult.getThrowable())
                    .skip(iTestResult.getThrowable());

            String category = getTestMethodCategory(iTestResult);
            if (category != null) {
                currentTest.assignCategory(category);
            }
        }
    }

    public void flushExtentReport() {
        extentReports.flush();
    }

    public void endTest() {
        testMap.remove(Thread.currentThread().getId());
    }
}
