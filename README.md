# API Automation Using Rest Assured & TestNG

This project provides a template for automating REST API testing across multiple environments using TestNG and the Rest Assured library.

[![Rest Assured Java API Automation CI](https://github.com/osandadeshan/rest-assured-java-api-automation-demo/actions/workflows/rest-assured-java-ci.yml/badge.svg?branch=master)](https://github.com/osandadeshan/rest-assured-java-api-automation-demo/actions/workflows/rest-assured-java-ci.yml)

## Prerequisites
1. Java
2. Maven
3. IntelliJ IDEA

## Project structure

```
.github/                                              # GitHub workflow configurations
logs/                                                 # Log4j log files are stored here
reports/                                              # Extent report files are generated in this location
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── restassured/
│   │           └── example/
│   │               ├── Category.java
│   │               ├── HttpMethod.java
│   │               ├── constant/                     # Application-wide constants
│   │               │   ├── ApplicationConstant.java
│   │               │   ├── AuthenticationConstant.java
│   │               │   ├── CommonConstant.java
│   │               │   └── ReporterConstant.java
│   │               ├── service/
│   │               │   ├── ExtentReportService.java  # Extent report setup services
│   │               │   └── app/                      # Application service classes
│   │               │       ├── AuthenticationService.java
│   │               │       └── UserService.java
│   │               └── util/                         # Utility methods
│   │                   ├── AnnotationReader.java
│   │                   └── FileReader.java
│   │                   └── JsonFormatter.java
│   │                   └── Log4jFilter.java
│   │                   └── RestClient.java
│   │                   └── TestListener.java
│   └── resources/                                    # Resource files for logging and reporting
│       ├── log4j2.xml
│       └── test-reporter.properties
├── test/
│   ├── java/
│   │   └── com/
│   │       └── restassured/
│   │           └── example/
│   │               └── test/
│   │                   ├── constant/                     # Test constants (e.g., status, categories)
│   │                   │   ├── Gender.java
│   │                   │   ├── Status.java
│   │                   │   └── TestCategory.java
│   │                   ├── AuthTest.java                 # Authentication tests
│   │                   ├── BaseTest.java                 # Base test class setup
│   │                   ├── CreateBookingTest.java        # API test for creating a booking
│   │                   ├── DeleteBookingTest.java        # API test for deleting a booking
│   │                   ├── GetAllBookingsTest.java       # API test for fetching all bookings
│   │                   └── UpdateBookingTest.java        # API test for updating a booking
│   └── resources/
│       └── env/                                          # Environment configurations
│           ├── dev.properties
│           ├── pre-prod.properties
│           ├── prod.properties
│           ├── qa.properties
│           └── uat.properties
│       └── regression-suite.xml                          # TestNG test suites configuration
│       └── smoke-suite.xml
target/                                                   # Compiled code and build artifacts are stored here
.gitignore                                                # Specifies files and directories to be ignored by Git
LICENSE                                                   # License information for the project
pom.xml                                                   # Maven configuration file for dependencies and build settings
README.md                                                 # Repository overview and instructions (This file)
```

## ⚠️ Important: Steps to update the APIs
* Go to `com.restassured.example.util.RestClient`
* **Uncomment line 131** to enable your actual API functionality.
* Remove the **"TODO: "** comment, as it is no longer relevant

## How to run tests
1. Using IntelliJ IDEA
    * Go to Maven Profiles
    * Select `dev`, `qa`, `uat`, `pre-prod` or `prod` Maven Profile as the environment
    * Select the test classes on the `src/test/java` folder
    * Right-click and click on `Run`


2. Using Command Line
    * To run the smoke test suite against the UAT environment

      `mvn clean test -Puat,smoke-test`

    * To run the regression test suite against the QA environment

      `mvn clean test -Pqa,regression-test`

**Note**: By default, if no Maven profiles are selected, the tests will be executed on the `dev` environment.

## License
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/License_icon-mit-2.svg/2000px-License_icon-mit-2.svg.png" alt="MIT License" width="100" height="100"/> [MIT License](https://opensource.org/licenses/MIT)

## Copyright
Copyright 2024 [MaxSoft](https://maxsoftlk.github.io/).
