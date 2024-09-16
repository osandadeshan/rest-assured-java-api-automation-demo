package com.restassured.example.constant;

import com.restassured.example.util.FileReader;

import java.io.File;

public class ReporterConstant {
    public static final String FILE_SEPARATOR = File.separator;
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    public static final String TEST_REPORTER_PROPERTY_FILE_DIRECTORY = PROJECT_DIRECTORY + FILE_SEPARATOR
            + "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR
            + "test-reporter.properties";
    public static final String EXTENT_FULL_REPORT_DIRECTORY = FileReader.getTestReporterProperty("extent_full_report_dir");
    public static final String EXTENT_REPORT_FILE_NAME_PREFIX = FileReader.getTestReporterProperty("extent_report_file_name_prefix");
    public static final String EXTENT_REPORT_THEME = FileReader.getTestReporterProperty("extent_reporter_theme");
    public static final String EXTENT_REPORT_DOCUMENT_TITLE = FileReader.getTestReporterProperty("extent_document_title");
    public static final String EXTENT_REPORT_REPORTER_NAME = FileReader.getTestReporterProperty("extent_reporter_name");
    public static final String APPLICATION_NAME = FileReader.getTestReporterProperty("application_name");
    public static final String TEST_DEVELOPER = FileReader.getTestReporterProperty("test_developer");
}
