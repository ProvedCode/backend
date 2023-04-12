package com.provedcode.user.service;

import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.UserInfoDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticationService {
    UserInfoDTO login(String name, Collection<? extends GrantedAuthority> authorities);
    UserInfoDTO register(RegistrationDTO user);
}
