package com.provedcode.talent.model.dto;

import com.provedcode.talent.model.ProofStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import org.hibernate.validator.constraints.URL;

@Builder
public record ProofDTO(
        long id,
        @URL
        String link,
        String text,
        @NotNull
        ProofStatus status,
        String created
) {
}