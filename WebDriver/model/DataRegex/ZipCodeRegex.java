package model.DataRegex;

public class ZipCodeRegex {
    private static final String ZIP_CODE_REGEX = "^[0-9]{5}-?[0-9]{3}$";

    public static String getZipCodeRegex() { return ZIP_CODE_REGEX; }
}
