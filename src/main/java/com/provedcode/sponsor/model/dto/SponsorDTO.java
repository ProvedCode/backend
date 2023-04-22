package com.provedcode.sponsor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SponsorDTO(
        Long id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String image
) {
}