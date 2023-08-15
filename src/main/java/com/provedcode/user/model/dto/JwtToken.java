package com.provedcode.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record JwtToken(
        @JsonProperty("jwt-token")
        String token
) {
}
