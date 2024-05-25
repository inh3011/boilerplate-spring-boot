package com.boilerplate.domain.enumuration;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    USER("사용자"),
    ADMIN("관리자")
    ;
    private final String description;

    Role(String description) {
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this;
    }
}
