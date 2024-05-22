package com.boilerplate.exception.response;

import com.boilerplate.exception.enumuration.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FieldExceptionResponse extends ExceptionResponse {
    private final List<FieldError> fieldErrors;

    private FieldExceptionResponse(ExceptionCode exceptionCode, List<FieldError> fieldErrors) {
        super(exceptionCode);
        this.fieldErrors = fieldErrors;
    }

    public FieldExceptionResponse(HttpStatus status, String code, String message, List<FieldError> fieldErrors) {
        super(status, code, message);
        this.fieldErrors = fieldErrors;
    }

    public static FieldExceptionResponse make(ExceptionCode exceptionCode, BindingResult bindingResult) {
        List<FieldError> fieldErrors = FieldError.of(bindingResult);
        String message = fieldErrors.stream()
                .map(fieldError -> fieldError.reason)
                .collect(Collectors.joining(System.lineSeparator()));

        return new FieldExceptionResponse(exceptionCode.getHttpStatus(), exceptionCode.name(), message, fieldErrors);
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    )).toList();
        }
    }
}
