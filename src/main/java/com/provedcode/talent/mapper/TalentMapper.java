package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TalentMapper {
    ShortTalentDTO talentToShortTalentDTO(Talent talent);

    FullTalentDTO talentToFullTalentDTO(Talent talent);

}
