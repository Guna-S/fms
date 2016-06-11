package com.fms.core.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Entity Not Found",value = HttpStatus.NOT_FOUND)
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 26073581746644057L;


}
