package com.provedcode.talent.model.dto;

import lombok.Builder;

@Builder
public record StatisticsDTO(
        Long allKudosOnTalent
) {
}
