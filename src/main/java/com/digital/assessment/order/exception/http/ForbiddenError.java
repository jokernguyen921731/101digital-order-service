package com.digital.assessment.order.exception.http;
import com.vsm.vin.common.misc.exception.http.ResponseError;

public enum ForbiddenError implements ResponseError {
    FORBIDDEN(4030000, "FORBIDDEN"),
    ACCOUNT_NOT_ACTIVATED(4030001, "ACCOUNT_NOT_ACTIVATED"),
    ;
    private Integer code;
    private String message;



    ForbiddenError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return 403;
    }
    @Override
    public Integer getCode() {
        return code;
    }
}
