package com.provedcode.user.service.impl;

import com.provedcode.user.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    public Jwt generateJwtToken(Long id, String login, Collection<? extends GrantedAuthority> authorities) {
        log.info("jwt-token generation: id = {%s}, authorities = {%s}".formatted(id, authorities));
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(60, MINUTES))
                .subject(login)
                .claim("id", id)
                .claim("scope", authorities.stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" ")))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }
}
