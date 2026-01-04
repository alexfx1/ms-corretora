package com.corretora.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExceptionResponse {

    private int errorCode;
    private String message;
    private List<String> errors;

    public ExceptionResponse(int errorCode, String message, List<String> errors) {

        super();
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    public ExceptionResponse(int errorCode, String message) {

        super();
        this.errorCode = errorCode;
        this.message = message;
    }
}
