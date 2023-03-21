package com.provedcode.service.impl;

import com.provedcode.config.PageConfig;
import com.provedcode.service.TalentService;
import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.repo.TalentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    TalentMapper talentMapper;
    TalentRepository talentRepository;
    PageConfig pageConfig;

    @Override
    public List<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size) {
        log.info("page = {}", pageConfig);
        return talentRepository.getTalentsPage(
                    PageRequest.of(page.orElse(pageConfig.defaultPageNum()), size.orElse(pageConfig.defaultPageSize())))
                .stream().map(i -> talentMapper.talentToShortTalentDTO(i))
                .toList();
    }
}
