package com.provedcode.talent.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ShortTalentDTO(
        Long id,
        String image,
        String firstname,
        String lastname,
        String specialization,
        List<String> skills
) {
}
