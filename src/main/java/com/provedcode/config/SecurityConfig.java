package com.provedcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c
                .requestMatchers("/actuator/health").permitAll() // for DevOps
                .requestMatchers(antMatcher("/h2/**")).permitAll()
                .requestMatchers(antMatcher("/api/**")).permitAll()
                .anyRequest().denyAll()
        );
        http.csrf().disable().headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        return http.build();
    }
}
