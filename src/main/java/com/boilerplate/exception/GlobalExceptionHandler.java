package com.boilerplate.exception;

import com.boilerplate.exception.enumuration.ExceptionCode;
import com.boilerplate.exception.response.ExceptionResponse;
import com.boilerplate.exception.response.ExceptionResponseEntityFactory;
import com.boilerplate.exception.response.FieldExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("REQUEST URI: {}, MESSAGE: {}", request.getRequestURI(), e.getMessage());

        return ExceptionResponseEntityFactory.make(e.getHttpStatus(), e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ExceptionResponse> noSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        log.error("REQUEST URI: {}, MESSAGE: {}", request.getRequestURI(), e.getMessage());

        return ExceptionResponseEntityFactory.make(ExceptionCode.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), exception);

        return ResponseEntity.status(ExceptionCode.INVALID_REQUEST_BODY.getHttpStatus()).body(FieldExceptionResponse.make(ExceptionCode.INVALID_REQUEST_BODY, exception.getBindingResult()));
    }
}