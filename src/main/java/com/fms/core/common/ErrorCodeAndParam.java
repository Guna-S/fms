package com.fms.core.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

@SuppressWarnings("OverloadedVarargsMethod")
public class ErrorCodeAndParam {

    private final String errorCode;
    private final String[] params;
    @JsonIgnore
    private final HttpStatus httpStatus;


    public ErrorCodeAndParam(final ErrorCode errorCode, final String... params) {
        this.errorCode = errorCode.getStatus();
        this.params = params;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String[] getParams() {
        return params;
    }
}
