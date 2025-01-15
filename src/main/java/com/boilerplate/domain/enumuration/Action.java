package com.boilerplate.domain.enumuration;

import lombok.Getter;

@Getter
public enum Action {
    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제"),
    ;
    private final String description;

    Action(String description) {
        this.description = description;
    }
}
