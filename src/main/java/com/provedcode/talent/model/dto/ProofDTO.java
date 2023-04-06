package com.provedcode.talent.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
public record ProofDTO(
        long id,
        String link,
        String text,
        String status,
        String created
) {
}
