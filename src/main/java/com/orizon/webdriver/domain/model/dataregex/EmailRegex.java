package com.orizon.webdriver.domain.model.dataregex;

public class EmailRegex {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static String getEmailRegex() { return EMAIL_REGEX; }

}