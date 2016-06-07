package com.fms.core.common;

public enum ErrorCode {

    FILE_WRITING_FAILED("FMS_0002"),
    NOT_FOUND("FMS_0001");

    ErrorCode(final String status) {
        this.status = status;

    }

    private final String status;

    public String getStatus() {
        return status;
    }
}
