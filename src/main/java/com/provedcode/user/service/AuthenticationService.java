package com.provedcode.user.service;

import com.provedcode.user.model.dto.RegistrationDTO;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String login(Authentication authentication);
    String register(RegistrationDTO user);
}
