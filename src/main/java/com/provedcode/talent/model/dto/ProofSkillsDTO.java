package com.provedcode.talent.model.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record ProofSkillsDTO(
        @Positive
        List<Long> skills
) {
}
