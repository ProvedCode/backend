package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentSkill;

public interface TalentMapper {
    default ShortTalentDTO talentToShortTalentDTO(Talent talent) {
        return ShortTalentDTO.builder()
                .id(talent.getId())
                .image(talent.getImage())
                .firstname(talent.getFirstName())
                .lastname(talent.getLastName())
                .specialization(talent.getSpecialization())
                .skills(talent.getTalentSkills().stream().map(TalentSkill::getSkill).toList())
                .build();
    }
}
