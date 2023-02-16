package com.digital.assessment.order.util;

import java.util.regex.Pattern;

public interface Const {
    Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    String BRANCH_ID_HEADER = "x-branch-id";
    String SEQUENCE_JOIN_KEY = "-";
    int DEFAULT_MAX_COUNT = 100;
    String PHONE_REGEX = "(\\+[0-9]+[\\- \\.]*)?" + "(\\([0-9]+\\)[\\- \\.]*)?" + "([0-9][0-9\\- \\.]+[0-9])";
    interface FULL_NAME {
        int NAME_MAX_LENGTH = 100;
        int NAME_MIN_LENGTH = 3;
    }

    interface USERNAME {
        int USERNAME_MAX_LENGTH = 20;
        int USERNAME_MIN_LENGTH = 3;
    }

    interface PASSWORD {
        int PW_MAX_LENGTH = 20;
        int PW_MIN_LENGTH = 8;
        String PW_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\^$*!@#%&])\\S{8,40}$";
    }

    interface EMAIL {
        int EMAIL_MAX_LENGTH = 50;
        String EMAIL_REGEX = "^([a-z0-9.-]+@[a-z0-9.-]+\\.[a-z]{2,3})$";
    }

    interface CLIENT {
        String CLIENT_ID = "f1354986-e698-4712-ba94-e94b10bf1b58";
        String CLIENT_SECRET = "fe2cd8c5-03b4-465c-9007-27eda0a99226";
    }
}
