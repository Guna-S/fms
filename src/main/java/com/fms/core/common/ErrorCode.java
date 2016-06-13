package com.fms.core.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {


    NOT_FOUND("FMS_0001", HttpStatus.NOT_FOUND),
    FILE_WRITING_FAILED("FMS_0002", HttpStatus.INTERNAL_SERVER_ERROR),
    DOC_FILE_SEQUENCE("FMS_0003", HttpStatus.BAD_REQUEST);

    ErrorCode(final String status, final HttpStatus httpStatus) {
        this.status = status;
        this.httpStatus = httpStatus;
    }

    private final String status;
    private final HttpStatus httpStatus;

    public String getStatus() {
        return status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
