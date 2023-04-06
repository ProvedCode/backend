package com.provedcode.user.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TALENT("ROLE_TALENT");
    private final String userRole;

    Role(String role) {
        this.userRole = role;
    }

    @Override
    public String getAuthority() {
        return this.userRole;
    }
}