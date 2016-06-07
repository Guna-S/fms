package com.fms.core.common;

/**
 * Created by Ganesan on 02/06/16.
 */
public class ErrorCodeAndParam {

    private final ErrorCode errorCode;
    private final String[] params;
    private  Throwable cause;

    public ErrorCodeAndParam(final ErrorCode errorCode,final String... params) {
        this.params = params;
        this.errorCode = errorCode;
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
