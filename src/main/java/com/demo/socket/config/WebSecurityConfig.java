package com.demo.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This web security setup is required to allow the endpoint "/test/hello" to be accessed without authentication.
 * All other endpoints will require authentication via OAuth2 Resource Server with JWT.
 * The "/test/hello" endpoint is used for readiness probe in deployment.yaml hence it needs to be accessed without authentication.
 * Without this configuration, the readiness probe would fail(with 401 unauthorized) resulting in the pod being marked as not ready.
 * If there were no readiness probe, then this configuration would not be necessary, as oauth2 resource server details provided in application.yaml should be enough for spring boot to auto-configure security.
 * If we are using this configuration, then we have to add the "oauth2ResourceServer" option here as well, otherwise there is no way for spring security to know how to authenticate other requests.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/test/hello").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}