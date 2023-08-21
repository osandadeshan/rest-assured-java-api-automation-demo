package constant;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 18/8/23
 * Time            : 1:04 pm
 * Description     :
 **/

public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String asString() {
        return status;
    }
}
