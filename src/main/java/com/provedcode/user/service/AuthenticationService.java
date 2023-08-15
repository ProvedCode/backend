package com.provedcode.user.service;

import com.provedcode.user.model.dto.SponsorRegistrationDTO;
import com.provedcode.user.model.dto.TalentRegistrationDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public interface AuthenticationService {
    Jwt login(String name, Collection<? extends GrantedAuthority> authorities);

    void activateAccount(String uuid);

    Jwt register(TalentRegistrationDTO talent);
    Jwt register(SponsorRegistrationDTO sponsor);
}
