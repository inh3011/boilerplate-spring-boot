package com.boilerplate.presentation.exception;

import com.boilerplate.presentation.exception.enumuration.ExceptionCode;

public class CookieException extends BusinessException {
    public CookieException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
