package com.provedcode.talent.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ShortTalentDTO(
        Long id,
        String image,
        String firstName,
        String lastName,
        String specialization,
        List<String> talents
) {
}
