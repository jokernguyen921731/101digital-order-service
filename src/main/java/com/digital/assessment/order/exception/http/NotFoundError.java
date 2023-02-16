package com.digital.assessment.order.exception.http;

import com.vsm.vin.common.misc.exception.http.ResponseError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum NotFoundError implements ResponseError {
    USER_NOT_FOUND(4044000, "USER_NOT_FOUND"),
    BRANCH_NOT_FOUND(4044003, "BRANCH_NOT_FOUND"),
    MENU_NOT_FOUND(4044001, "MENU_NOT_FOUND"),
    ORDER_NOT_FOUND(4044002, "ORDER_NOT_FOUND"),
    QUEUE_NOT_FOUND(4044003, "QUEUE_NOT_FOUND"),
    ;


    private int errorCode;
    private String message;

    NotFoundError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public Integer getCode() {
        return errorCode;
    }
}
