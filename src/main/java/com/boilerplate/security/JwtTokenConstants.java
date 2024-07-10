package com.boilerplate.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "boilerplate.jwt")
public class JwtTokenConstants {
    private long accessTokenExpired;
    private String accessTokenName;

    private long refreshTokenExpired;
    private String refreshTokenName;

    private String domain;
}
