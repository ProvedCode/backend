package com.provedcode.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public interface JwtService {
    Jwt generateJwtToken(Long id, String login, Collection<? extends GrantedAuthority> authorities);

}
