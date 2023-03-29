package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.user.model.dto.SessionInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface TalentService {
    Page<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size);

    FullTalentDTO getTalentById(long id);

    FullTalentDTO editTalent(long id, FullTalentDTO fullTalent, Authentication authentication);

    SessionInfoDTO deleteTalentById(long id, Authentication authentication);
}