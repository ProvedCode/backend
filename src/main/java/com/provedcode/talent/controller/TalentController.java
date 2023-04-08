package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.service.TalentService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TalentController {
    TalentService talentService;
    TalentMapper talentMapper;

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/talents/{id}")
    FullTalentDTO getTalent(@PathVariable("id") long id, Authentication authentication) {
        log.info("get-talent auth = {}", authentication);
        log.info("get-talent auth.name = {}", authentication.getAuthorities());
        return talentMapper.talentToFullTalentDTO(talentService.getTalentById(id));
    }

    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    Page<ShortTalentDTO> getTalents(@RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size) {
        return talentService.getTalentsPage(page, size).map(talentMapper::talentToShortTalentDTO);
    }

    @PreAuthorize("hasRole('TALENT')")
    @PatchMapping("/talents/{talent-id}")
    FullTalentDTO editTalent(@PathVariable("talent-id") long id,
                             @RequestBody @Valid FullTalentDTO fullTalent,
                             Authentication authentication) {
        return talentMapper.talentToFullTalentDTO(talentService.editTalent(id, fullTalent, authentication));
    }

    @PreAuthorize("hasRole('TALENT')")
    @DeleteMapping("/talents/{id}")
    SessionInfoDTO deleteTalent(@PathVariable("id") long id, Authentication authentication) {
        return talentService.deleteTalentById(id, authentication);
    }
}