package com.fms.core.common;
@SuppressWarnings("OverloadedVarargsMethod")
public class ErrorCodeAndParam {

    private final ErrorCode errorCode;
    private final String[] params;
    private final Throwable cause;


    public ErrorCodeAndParam(final ErrorCode errorCode, final String... params) {
        this(new ValidationException(), errorCode, params);
    }

    public ErrorCodeAndParam(final Throwable cause, final ErrorCode errorCode, final String... params) {
        this.errorCode = errorCode;
        this.params = params;
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String[] getParams() {
        return params;
    }
}
