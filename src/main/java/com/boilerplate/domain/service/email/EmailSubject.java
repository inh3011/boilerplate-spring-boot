package com.boilerplate.domain.service.email;

import lombok.Getter;

@Getter
public enum EmailSubject {
    WELCOME("Welcome to our service!"),
    PASSWORD_RESET("Password Reset Request"),
    NEWSLETTER("Our Latest Newsletter");

    private final String subject;

    EmailSubject(String subject) {
        this.subject = subject;
    }

}