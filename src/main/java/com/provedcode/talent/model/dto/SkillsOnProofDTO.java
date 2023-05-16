package com.provedcode.talent.model.dto;

import com.provedcode.talent.model.entity.Skills;
import lombok.Builder;

import java.util.Set;

@Builder
public record SkillsOnProofDTO(
        Set<Skills> skills
) {
}
