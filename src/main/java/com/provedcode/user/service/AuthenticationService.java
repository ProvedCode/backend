package com.provedcode.user.service;

import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.SessionInfoDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationService {
    SessionInfoDTO login(String name, Collection<? extends GrantedAuthority> authorities);
    SessionInfoDTO register(RegistrationDTO user);
}
