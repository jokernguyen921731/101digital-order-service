package com.digital.assessment.order.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForwardResponseException extends RuntimeException {
    private int code;
    private String message;
    private String error;
    private int status;

    public ForwardResponseException(int code, String message, String error, int status) {
        this(code, message ,error, status, null);
    }

    public ForwardResponseException(int code, String message, String error , int status, Throwable cause) {
        super(message, null);
        this.code = code;
        this.message = message;
        this.error = error;
        this.status = status;
    }

}
