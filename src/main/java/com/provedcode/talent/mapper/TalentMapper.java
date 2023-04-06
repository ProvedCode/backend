package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TalentMapper {
    default FullTalentDTO talentToFullTalentDTO(Talent talent) {
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
                            .talents(talent.getTalentTalents().stream().map(TalentTalents::getTalentName).toList())
                            .attachedFiles(
                                    talent.getTalentAttachedFiles().stream().map(TalentAttachedFile::getAttachedFile)
                                          .toList())
                            .build();
    }

    ShortTalentDTO talentToShortTalentDTO(Talent talent);
}
