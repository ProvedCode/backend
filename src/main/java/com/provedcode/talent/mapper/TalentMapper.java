package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;

public interface TalentMapper {

    ShortTalentDTO talentToShortTalentDTO(Talent talent);
    FullTalentDTO talentToFullTalentDTO(Talent talent);

}
