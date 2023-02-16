package com.digital.assessment.order.util;

import java.util.regex.Matcher;

public class ValidatorUtils {

    public static boolean validateEmail(String emailStr) {
        if (emailStr.length() > 255) {
            return false;
        }
        Matcher matcher = Const.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
