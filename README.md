# API Automation Using Rest Assured & TestNG

This project serves as a boilerplate for automating REST APIs across various environments using TestNG and the Rest Assured library.

[![Rest Assured Java API Automation CI](https://github.com/osandadeshan/selenium-java-web-automation-demo/actions/workflows/selenium-java-ci.yml/badge.svg?branch=master)](https://github.com/osandadeshan/selenium-java-web-automation-demo/actions/workflows/selenium-java-ci.yml)

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
    * To run tests against the UAT environment

      `mvn clean test -Puat`

**Note**: By default, if no Maven profiles are selected, the tests will be executed on the `dev` environment.

## License
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/License_icon-mit-2.svg/2000px-License_icon-mit-2.svg.png" alt="MIT License" width="100" height="100"/> [MIT License](https://opensource.org/licenses/MIT)

## Copyright
Copyright 2023 [MaxSoft](https://maxsoftlk.github.io/).