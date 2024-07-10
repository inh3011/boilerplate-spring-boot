package com.boilerplate.security.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class JwtAuthenticationDto {

    public record JwtAuthenticationRequest(
            String username,
            String password
    ) {
        public static JwtAuthenticationRequest create(HttpServletRequest request) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), JwtAuthenticationRequest.class);
        }
    }

    public record JwtAuthenticationResponse(
            String accessToken,
            String refreshToken
    ) {
        public static JwtAuthenticationResponse of(
                String accessToken,
                String refreshToken
        ) {
            return new JwtAuthenticationResponse(
                accessToken,
                refreshToken
            );
        }

    }
}
