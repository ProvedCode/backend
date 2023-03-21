package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;

import java.util.List;
import java.util.Optional;


public interface TalentService {
    List<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size);

    FullTalentDTO getTalentById(long id);
}