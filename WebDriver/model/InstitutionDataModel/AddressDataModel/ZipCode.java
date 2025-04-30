package model.InstitutionDataModel.AddressDataModel;

import model.DataRegex.ZipCodeRegex;

import java.util.Objects;
import java.util.regex.Pattern;

public class ZipCode {

    private static final Pattern pattern = Pattern.compile(ZipCodeRegex.getZipCodeRegex());

    private String zipcode;


    public ZipCode(String zipcode) {
        validate(zipcode);
        this.zipcode = zipcode.replace("-", "");
    }

    public String getFormattedZipCode() {
        return zipcode.substring(0, 5) + "-" + zipcode.substring(5);
    }

    public String getZipCodeDigits() { return this.zipcode; }

    private void validate(String zipcode){
        Objects.requireNonNull(zipcode, "Zip code cannot be null.");
        if(!pattern.matcher(zipcode).matches()){
            throw new IllegalArgumentException("An invalid zip code was used.");
        }
    }
}
