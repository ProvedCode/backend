package com.provedcode.talent.model.dto;

import lombok.Builder;

@Builder
public record ProofDTO(
        long id,
        String proof
) {
}
