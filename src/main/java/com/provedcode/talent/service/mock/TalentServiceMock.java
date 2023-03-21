package com.provedcode.talent.service.mock;

import com.provedcode.config.PageConfig;
import com.provedcode.talent.service.TalentService;
import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//@Service
@Slf4j
@AllArgsConstructor
public class TalentServiceMock implements TalentService {
    TalentMapper talentMapper;
    TalentRepository talentRepository;
    PageConfig pageConfig;

    @Override
    public List<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.orElse(pageConfig.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageConfig.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        return talentRepository.getTalentsPage(
                        PageRequest.of(page.orElse(pageConfig.defaultPageNum()), size.orElse(pageConfig.defaultPageSize())))
                .stream().map(i -> talentMapper.talentToShortTalentDTO(i))
                .toList();
    }

    @Override
    public FullTalentDTO getTalentById(long id) {
        Optional<Talent> talent = talentRepository.findById(id);
        if (talent.isEmpty()){
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }
        return talentMapper.talentToFullTalentDTO(talent.get());
    }
}