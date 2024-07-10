package com.boilerplate.security;

import com.boilerplate.presentation.exception.BusinessException;
import com.boilerplate.presentation.exception.enumuration.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Long JWT_ACCESS_VALID_TIME = 900_000L;
    private static final Long JWT_REFRESH_VALID_TIME = 1_296_000_000L;

    public String generateAccessToken(PrincipalDetails principalDetails) {
        return Jwts.builder()
                .setSubject((principalDetails.getUsername()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_VALID_TIME))
                .claim("roles", principalDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(PrincipalDetails principalDetails) {
        return Jwts.builder()
                .setSubject((principalDetails.getUsername()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_VALID_TIME))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("토큰이 만료되었습니다.");
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            log.warn("JWT 시그니처가 올바르지 않습니다.");
            throw new BusinessException(ExceptionCode.TOKEN_ERROR, "JWT 시그니처가 올바르지 않습니다.");
        } catch (IllegalArgumentException e) {
            log.warn("claim 인증되지 않았습니다.");
            throw new BusinessException(ExceptionCode.TOKEN_ERROR, "claims이 인증되지 않았습니다.");
        } catch (MalformedJwtException e) {
            log.warn("올바르지 않은 claim 토큰에 포함되어 있습니다.");
            throw new BusinessException(ExceptionCode.TOKEN_ERROR, "올바르지 않은 cliams이 토큰에 포함되어 있습니다.");
        }
    }
}
