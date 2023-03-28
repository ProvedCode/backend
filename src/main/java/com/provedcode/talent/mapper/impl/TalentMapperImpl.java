package com.provedcode.talent.mapper.impl;

import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TalentMapperImpl implements TalentMapper {
    @Override
    public ShortTalentDTO talentToShortTalentDTO(Talent talent) {
        return ShortTalentDTO.builder()
                             .id(talent.getId())
                             .image(talent.getImage())
                             .firstName(talent.getFirstName())
                             .lastName(talent.getLastName())
                             .specialization(talent.getSpecialization())
                             .skills(talent.getTalentSkills().stream().map(TalentSkill::getSkill).toList())
                             .build();
    }

    @Override
    public FullTalentDTO talentToFullTalentDTO(Talent talent) {
        return FullTalentDTO.builder()
                            .id(talent.getId())
                            .firstName(talent.getFirstName())
                            .lastName(talent.getLastName())
                            .bio(talent.getTalentDescription() != null ? talent.getTalentDescription().getBio() : null)
                            .additionalInfo(talent.getTalentDescription() != null ? talent.getTalentDescription()
                                                                                          .getAdditionalInfo() : null)
                            .image(talent.getImage())
                            .specialization(talent.getSpecialization())
                            .links(talent.getTalentLinks().stream().map(TalentLink::getLink).toList())
                            .contacts(talent.getTalentContacts().stream().map(TalentContact::getContact).toList())
                            .skills(talent.getTalentSkills().stream().map(TalentSkill::getSkill).toList())
                            .attachedFiles(
                                    talent.getTalentAttachedFiles().stream().map(TalentAttachedFile::getAttachedFile)
                                          .toList())
                            .build();
    }
}
