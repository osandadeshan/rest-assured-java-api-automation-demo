package com.restassured.example.util;

import com.restassured.example.constant.CommonConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import static com.restassured.example.constant.ReporterConstant.TEST_REPORTER_PROPERTY_FILE_DIRECTORY;

public class FileReader {
    private static final Logger logger = LogManager.getLogger();

    public static String getEnvironmentConfig(String propertyName) {
        String fileName = "env/" + CommonConstant.EXECUTION_ENV_NAME + ".properties";
        Path filePath = Paths.get(
                Objects.requireNonNull(
                        FileReader.class.getClassLoader().getResource(fileName)
                ).getPath()
        );
        return getPropertyFromFile(filePath, propertyName);
    }

    public static String getTestReporterProperty(String propertyName) {
        Path filePath = Paths.get(TEST_REPORTER_PROPERTY_FILE_DIRECTORY);
        return getPropertyFromFile(filePath, propertyName);
    }

    private static String getPropertyFromFile(Path filePath, String propertyName) {
        Properties properties = loadProperties(filePath);
        return properties.getProperty(propertyName);
    }

    private static Properties loadProperties(Path filePath) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties from file: {}", filePath);
            logger.error(e.getMessage());
        }
        return properties;
    }
}
