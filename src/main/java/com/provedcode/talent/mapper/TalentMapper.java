package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;

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

    default FullTalentDTO talentToFullTalentDTO(Talent talent) {
        return FullTalentDTO.builder()
                .id(talent.getId())
                .firstname(talent.getFirstName())
                .lastname(talent.getLastName())
                .bio(talent.getTalentDescription().getBio())
                .additionalInfo(talent.getTalentDescription().getAdditionalInfo())
                .image(talent.getImage())
                .specialization(talent.getSpecialization())
                .links(talent.getTalentLinks().stream().map(TalentLink::getLink).toList())
                .contacts(talent.getTalentContacts().stream().map(TalentContact::getContact).toList())
                .skills(talent.getTalentSkills().stream().map(TalentSkill::getSkill).toList())
                .attachedFiles(talent.getTalentAttachedFiles().stream().map(TalentAttachedFile::getAttachedFile).toList())
                .build();
    }

}
