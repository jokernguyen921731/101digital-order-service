package com.digital.assessment.order.exception.http;

import com.vsm.vin.common.misc.exception.http.ResponseError;
import lombok.Getter;

@Getter
public enum InvalidInputError implements ResponseError {
    BRANCH_EXIST(4004000, "BRANCH_EXIST"),
    INVALID_EMAIL(4004001, "INVALID_EMAIL"),
    QUEUE_EXIST(4004002, "QUEUE_EXIST"),
    CANNOT_INACTIVE_QUEUE(4004003, "CANNOT_INACTIVE_QUEUE"),
    MENU_EXIST(4004004, "MENU_EXIST"),
    QUEUE_SIZE_OVER(4004005, "QUEUE_SIZE_OVER"),
    INVALID_TOTAL_COST(4004006, "INVALID_TOTAL_COST"),
    CANNOT_CANCEL_ORDER(4004007, "CANNOT_CANCEL_ORDER"),
    CANNOT_TAKE_ORDER(4004008, "CANNOT_TAKE_ORDER"),
    ;
    private Integer code;
    private String message;

    InvalidInputError(Integer code, String message) {
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
        return 400;
    }

    public Integer getCode() {
        return code;
    }
}
