package com.provedcode.talent.model.dto;

import com.provedcode.talent.model.ProofStatus;
import lombok.Builder;

@Builder
public record ProofDTO(
        long id,
        String link,
        String text,
        ProofStatus status,
        String created
) {
}
