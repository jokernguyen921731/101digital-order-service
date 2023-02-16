package com.digital.assessment.order.exception.http;

import com.vsm.vin.common.misc.exception.http.ResponseError;

public enum AuthorizedError implements ResponseError {
    NON_AUTHORIZED_INFORMATION(4010001, "NON_AUTHORIZED_INFORMATION"),
            ;
    private Integer code;
    private String message;

    AuthorizedError(Integer code, String message) {
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
        return 401;
    }
    @Override
    public Integer getCode() {
        return code;
    }
}
