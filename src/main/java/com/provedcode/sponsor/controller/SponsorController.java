package com.provedcode.sponsor.controller;

import com.provedcode.sponsor.mapper.SponsorMapper;
import com.provedcode.sponsor.model.dto.SponsorDTO;
import com.provedcode.sponsor.model.dto.SponsorEditDTO;
import com.provedcode.sponsor.service.SponsorService;
import com.provedcode.talent.model.dto.FullTalentDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3")
public class SponsorController {
    SponsorService sponsorService;
    SponsorMapper sponsorMapper;

    @GetMapping("/sponsors")
    @ResponseStatus(HttpStatus.OK)
    Page<SponsorDTO> getSponsors(@RequestParam(value = "page") Optional<Integer> page,
                                 @RequestParam(value = "size") Optional<Integer> size) {
        return sponsorService.getAllSponsors(page, size).map(sponsorMapper::toDto);
    }

    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/sponsors/{id}")
    SponsorDTO getSponsor(@PathVariable("id") long id, Authentication authentication) {
        return sponsorMapper.toDto(sponsorService.getSponsorById(id, authentication));
    }

    @PreAuthorize("hasRole('SPONSOR')")
    @PatchMapping("/sponsors/{id}")
    SponsorDTO editSponsor(@PathVariable("id") long id,
                           @RequestBody SponsorEditDTO sponsorEditDTO,
                           Authentication authentication) {
        return sponsorMapper.toDto(sponsorService.editSponsorById(id, sponsorEditDTO, authentication));
    }

    @PreAuthorize("hasRole('SPONSOR')")
    @DeleteMapping("/sponsors/{id}")
    void deleteSponsor(@PathVariable("id") long id, Authentication authentication) {
        sponsorService.deleteSponsor(id, authentication);
    }

}