package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.user.model.dto.SessionInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import com.provedcode.talent.model.dto.FullTalentDTO;

import java.util.Optional;


public interface TalentService {
    Page<Talent> getTalentsPage(Optional<Integer> page, Optional<Integer> size);

    Talent getTalentById(long id);

    Talent editTalent(long id, EditTalent editTalent, Authentication authentication);

    SessionInfoDTO deleteTalentById(long id, Authentication authentication);
}