package com.boilerplate.exception.response;

import com.boilerplate.exception.enumuration.ExceptionCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

public class ExceptionResponseEntityFactory {
    private ExceptionResponseEntityFactory() {
    }

    public static ResponseEntity<ExceptionResponse> make(ExceptionCode exceptionCode) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionCode);
        new HttpHeaders().setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        return ResponseEntity.status(exceptionCode.getHttpStatus()).body(exceptionResponse);
    }

    public static ResponseEntity<ExceptionResponse> make(HttpStatus status, String code, String message) {
        ExceptionResponse errorResponse = new ExceptionResponse(status, code, message);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        return ResponseEntity.status(errorResponse.getStatus()).headers(httpHeaders).body(errorResponse);
    }

    public static ResponseEntity<ExceptionResponse> make(AuthenticationException authenticationException) {
        if (authenticationException instanceof LockedException) {
            return make(ExceptionCode.LOGIN_LOCKED);
        }
        if (authenticationException instanceof DisabledException) {
            return make(ExceptionCode.LOGIN_DISABLED);
        }

        if (authenticationException instanceof AccountExpiredException) {
            return make(ExceptionCode.LOGIN_EXPIRED);
        }
        if (authenticationException instanceof CredentialsExpiredException) {
            return make(ExceptionCode.LOGIN_CREDENTIAL_EXPIRED);
        }
        return make(ExceptionCode.LOGIN_FAILURE);
    }
}
