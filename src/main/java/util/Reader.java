package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static constant.CommonConstants.EXECUTION_ENV_NAME;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/8/23
 * Time            : 2:12 pm
 * Description     :
 **/

public class Reader {
    public static String getEnvironmentConfig(String propertyName) {
        String propertyValue = null;

        try (InputStream inputStream = Reader.class.getClassLoader()
                .getResourceAsStream(("env/" + EXECUTION_ENV_NAME + ".properties"))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return propertyValue;
    }
}
