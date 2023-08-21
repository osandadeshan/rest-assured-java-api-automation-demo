package constant;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 18/8/23
 * Time            : 1:04 pm
 * Description     :
 **/

public enum Gender {
    MALE("male"),
    FEMALE("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String asString() {
        return gender;
    }
}
