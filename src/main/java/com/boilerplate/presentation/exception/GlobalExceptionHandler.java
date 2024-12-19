package com.boilerplate.presentation.exception;

import com.boilerplate.presentation.exception.enumuration.ExceptionCode;
import com.boilerplate.presentation.exception.response.ExceptionResponse;
import com.boilerplate.presentation.exception.response.ExceptionResponseEntityFactory;
import com.boilerplate.presentation.exception.response.FieldExceptionResponse;
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
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception, HttpServletRequest request) {
        errorLog(request, exception.getMessage());

        return ExceptionResponseEntityFactory.make(exception.getHttpStatus(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ExceptionResponse> noSuchElementException(NoSuchElementException exception, HttpServletRequest request) {
        errorLog(request, exception.getMessage());

        return ExceptionResponseEntityFactory.make(ExceptionCode.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        errorLog(request, exception.getMessage());
        return ResponseEntity.status(ExceptionCode.INVALID_REQUEST_BODY.getHttpStatus()).body(FieldExceptionResponse.make(ExceptionCode.INVALID_REQUEST_BODY, exception.getBindingResult()));
    }

    public void errorLog(HttpServletRequest request, String errorMessage) {
        log.error("REQUEST URI: {}, MESSAGE: {}", request.getRequestURI(), errorMessage);
    }
}