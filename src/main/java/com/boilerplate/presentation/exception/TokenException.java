package com.boilerplate.presentation.exception;

import com.boilerplate.presentation.exception.enumuration.ExceptionCode;

public class TokenException extends BusinessException {
    public TokenException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public TokenException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
