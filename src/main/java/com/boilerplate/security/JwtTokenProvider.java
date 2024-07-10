package com.boilerplate.security;

import com.boilerplate.presentation.exception.BusinessException;
import com.boilerplate.presentation.exception.CookieException;
import com.boilerplate.presentation.exception.enumuration.ExceptionCode;
import com.boilerplate.security.service.OAuth2UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    private final JwtTokenConstants jwtTokenConstants;

    public String generateAccessToken(OAuth2UserPrincipal oauth2UserPrincipal) {
        return Jwts.builder()
                .setSubject((oauth2UserPrincipal.getUsername()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_VALID_TIME))
                .claim("roles", oauth2UserPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(OAuth2UserPrincipal oauth2UserPrincipal) {
        return Jwts.builder()
                .setSubject((oauth2UserPrincipal.getUsername()))
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

    public void setToken(HttpServletResponse response, String accessToken, String refreshToken) {
        try {
            Cookie refreshCookie = new Cookie(jwtTokenConstants.getRefreshTokenName(), refreshToken);
            refreshCookie.setDomain(jwtTokenConstants.getDomain());
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge((int) (jwtTokenConstants.getRefreshTokenExpired() / 1000) - 10);

            Cookie accessCookie = new Cookie(jwtTokenConstants.getAccessTokenName(), accessToken);
            accessCookie.setDomain(jwtTokenConstants.getDomain());
            accessCookie.setPath("/");
            accessCookie.setMaxAge((int) (jwtTokenConstants.getAccessTokenExpired() / 1000) - 10);

            response.addCookie(refreshCookie);
            response.addCookie(accessCookie);
        } catch (Exception exception) {
            throw new CookieException(ExceptionCode.COOKIE_EXCEPTION);
        }
    }
}
