package com.provedcode.talent.controller;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.talent.service.TalentService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.util.annotations.doc.controller.talent.DeleteTalentApiDoc;
import com.provedcode.util.annotations.doc.controller.talent.GetAllTalentsApiDoc;
import com.provedcode.util.annotations.doc.controller.talent.GetTalentApiDoc;
import com.provedcode.util.annotations.doc.controller.talent.PatchEditTalentApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "talent", description = "Talent API")
@Validated
public class TalentController {
    TalentService talentService;
    TalentMapper talentMapper;

    @GetAllTalentsApiDoc
    @GetMapping("/talents")
    @Validated
    Page<ShortTalentDTO> getTalents(@RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(1000) Integer size) {
        return talentService.getTalentsPage(page, size).map(talentMapper::talentToShortTalentDTO);
    }

    @GetTalentApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/talents/{id}")
    FullTalentDTO getTalent(@PathVariable("id") long id, Authentication authentication) {
        log.info("get-talent auth = {}", authentication);
        log.info("get-talent auth.name = {}", authentication.getAuthorities());
        return talentMapper.talentToFullTalentDTO(talentService.getTalentById(id));
    }

    @PatchEditTalentApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @PatchMapping("/talents/{talent-id}")
    FullTalentDTO editTalent(@PathVariable("talent-id") long id,
                             @RequestBody @Valid EditTalent editTalent,
                             Authentication authentication) {
        return talentMapper.talentToFullTalentDTO(talentService.editTalent(id, editTalent, authentication));
    }

    @DeleteTalentApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @DeleteMapping("/talents/{id}")
    SessionInfoDTO deleteTalent(@PathVariable("id") long id, Authentication authentication) {
        return talentService.deleteTalentById(id, authentication);
    }
}