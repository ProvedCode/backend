package com.provedcode.user.service;

import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.TokenDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationService {
    TokenDTO login(String name, Collection<? extends GrantedAuthority> authorities);
    TokenDTO register(RegistrationDTO user);
}
