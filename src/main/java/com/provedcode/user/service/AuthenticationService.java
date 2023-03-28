package com.provedcode.user.service;

import com.provedcode.user.model.dto.RegistrationDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationService {
    String login(String name, Collection<? extends GrantedAuthority> authorities);
    String register(RegistrationDTO user);
}
