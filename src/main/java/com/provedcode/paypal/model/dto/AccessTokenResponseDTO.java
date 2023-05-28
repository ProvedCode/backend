package com.provedcode.paypal.model.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccessTokenResponseDTO(String scope, @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType, @JsonProperty("app_id") String applicationId,
        @JsonProperty("expires_in") int expiresIn, String nonce, @JsonIgnore Instant expiration) {
}
