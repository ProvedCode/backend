package com.provedcode.service;

import com.provedcode.talent.model.dto.ShortTalentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TalentService {
    List<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size);
}