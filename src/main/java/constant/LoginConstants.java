package constant;

import static util.Reader.getEnvironmentConfig;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 7/8/23
 * Time            : 2:11 pm
 * Description     :
 **/

public class LoginConstants {
    public static final String LOGIN_USERNAME = getEnvironmentConfig("email_address");
    public static final String LOGIN_PASSWORD = getEnvironmentConfig("password");
}
