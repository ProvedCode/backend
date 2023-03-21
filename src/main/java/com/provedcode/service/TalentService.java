package com.provedcode.service;

import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TalentService {
    List<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size);

    Talent getTalentById(long id);
}