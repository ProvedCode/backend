package com.provedcode.user.model;

public enum Role {
    TALENT("ROLE_TALENT");
    private final String userRole;

    Role(String role) {
        this.userRole = role;
    }

    @Override
    public String toString() {
        return this.userRole;
    }
}
