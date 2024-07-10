package com.boilerplate.security;

import com.boilerplate.presentation.exception.enumuration.ExceptionCode;
import com.boilerplate.presentation.exception.response.ExceptionResponse;
import com.boilerplate.presentation.exception.response.ExceptionResponseEntityFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationManager authenticationManager = getAuthenticationManager();

        JwtAuthenticationDto.JwtAuthenticationRequest authenticationRequest;
        try {
            authenticationRequest = JwtAuthenticationDto.JwtAuthenticationRequest.create(request);
        } catch (IOException ioException) {
            throw new AuthenticationServiceException("객체 변환 과정에서 오류가 발생", ioException);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        // TODO : JWT 생성

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                JwtAuthenticationDto.JwtAuthenticationResponse.of(
                        "accessToken",
                        "refreshToken"
                )));

        super.successfulAuthentication(request, response, chain, authentication);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("401 UNAUTHORIZED ERROR, MESSAGE: {}, EXCEPTION: {}", ExceptionCode.INVALID_PASSWORD, failed);
        ResponseEntity<ExceptionResponse> responseEntity = ExceptionResponseEntityFactory.make(failed);

        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
    }
}
