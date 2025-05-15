package com.orizon.webdriver.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
public class ZipCode {

    private static final String ZIP_CODE_REGEX = "^[0-9]{5}-?[0-9]{3}$";

    private static final Pattern pattern = Pattern.compile(ZIP_CODE_REGEX);

    private String zipcode;


    public ZipCode(String zipcode) {
        validate(zipcode);
        this.zipcode = zipcode.replace("-", "");
    }

    public String getFormattedZipCode() {
        return zipcode.substring(0, 5) + "-" + zipcode.substring(5);
    }

    private void validate(String zipcode){
        Objects.requireNonNull(zipcode, "Zip code cannot be null.");
        if(!pattern.matcher(zipcode).matches()){
            throw new IllegalArgumentException("An invalid zip code was used.");
        }
    }
}
