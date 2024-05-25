package com.boilerplate.presentation.exception.response;

import com.boilerplate.presentation.exception.enumuration.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ExceptionResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;

    private final String timestamp;

    public ExceptionResponse(HttpStatus status, String code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public ExceptionResponse(ExceptionCode exceptionCode) {
        this.code = exceptionCode.name();
        this.message = exceptionCode.getMessage();
        this.status = exceptionCode.getHttpStatus();
        this.timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());


    }
}
