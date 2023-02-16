package com.digital.assessment.order.web.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ServiceResponse<T> succeed(HttpStatus status, T datta) {
        return new ServiceResponse(status.value(), (String)null, datta);
    }

    public static <T> ServiceResponse<T> succeed(HttpStatus status, String message, T datta) {
        return new ServiceResponse(status.value(), message, datta);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ServiceResponse)) {
            return false;
        } else {
            ServiceResponse<?> other = (ServiceResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ServiceResponse;
    }

    public int hashCode() {
        int result = 59 + this.getCode();
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        int var10000 = this.getCode();
        return "ServiceResponse(code=" + var10000 + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public ServiceResponse(final int code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
