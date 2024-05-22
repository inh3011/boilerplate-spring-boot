package com.boilerplate.exception;

import com.boilerplate.exception.enumuration.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
    protected final String code;
    protected final String message;
    protected final HttpStatus httpStatus;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.name();
        this.message = exceptionCode.getMessage();
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public BusinessException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.code = exceptionCode.name();
        this.message = message;
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public BusinessException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
