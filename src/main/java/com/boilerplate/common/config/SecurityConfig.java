package com.boilerplate.common.config;

import com.boilerplate.securiity.AuthorizationAccessDeniedHandler;
import com.boilerplate.securiity.AuthorizationNoAuthenticationHandler;
import com.boilerplate.securiity.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthorizationAccessDeniedHandler authorizationAccessDeniedHandler;
    private final AuthorizationNoAuthenticationHandler authorizationNoAuthenticationHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);

        return configurationSource;
    }

//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
//
//        authenticationFilter.setFilterProcessesUrl("/api/login");
//        authenticationFilter.setAuthenticationManager(authenticationManager());
//        return authenticationFilter;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF Disable
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .httpBasic(HttpBasicConfigurer::disable) // HTTP 기본 인증 Disable
                .formLogin(AbstractHttpConfigurer::disable) // formLogin Disable

                // 세션 기반 인증 사용 하지 않는다.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().permitAll());

        // Authorize ExceptionHandler
        http
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authorizationNoAuthenticationHandler)
                                .accessDeniedHandler(authorizationAccessDeniedHandler));

        http
//                .addFilter(jwtAuthenticationFilter()) // Authenticate Filter 추가
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
