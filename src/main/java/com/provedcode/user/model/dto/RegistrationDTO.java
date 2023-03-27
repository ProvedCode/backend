package com.provedcode.user.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RegistrationDTO(
        @NotEmpty String login,
        @NotEmpty String password,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty String specialization
) {
}
