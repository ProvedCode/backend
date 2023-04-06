package com.provedcode.talent.model.dto;

import com.provedcode.talent.model.ProofStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
public record ProofDTO(
        long id,
        String link,
        String text,
        ProofStatus status,
        String created
) {
}
