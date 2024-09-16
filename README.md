# API Automation Using Rest Assured & TestNG

This project serves as a boilerplate for automating REST APIs across various environments using TestNG and the Rest Assured library.

[![Rest Assured Java API Automation CI](https://github.com/osandadeshan/rest-assured-java-api-automation-demo/actions/workflows/rest-assured-java-ci.yml/badge.svg?branch=master)](https://github.com/osandadeshan/rest-assured-java-api-automation-demo/actions/workflows/rest-assured-java-ci.yml)

## Prerequisites
1. Java
2. Maven
3. NodeJS

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
