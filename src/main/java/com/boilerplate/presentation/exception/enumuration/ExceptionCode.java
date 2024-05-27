package com.boilerplate.presentation.exception.enumuration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "REQ BODY 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 자료가 존재하지 않습니다"),

    LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 맞지 않습니다."),
    LOGIN_LOCKED(HttpStatus.UNAUTHORIZED, "계정이 잠겼습니다. 관리자에게 문의해주세요"),
    LOGIN_DISABLED(HttpStatus.UNAUTHORIZED, "관리자 승인 대기중입니다."),
    LOGIN_EXPIRED(HttpStatus.UNAUTHORIZED, "계정이 만료되었습니다. 관리자에게 문의해주세요"),
    LOGIN_CREDENTIAL_EXPIRED(HttpStatus.UNAUTHORIZED, "계정 인증이 만료되었습니다. 관리자에게 문의해주세요."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // TOKEN
    TOKEN_NOT_FOUND_WHEN_REFRESH(HttpStatus.BAD_REQUEST, "REFRESH TOKEN 존재하지 않아 재발급 불가"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "로그인이 만료되었습니다."),
    TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "다시 로그인해주세요."),
    ;
    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
