package com.provedcode.sponsor.service;

import com.provedcode.config.PageProperties;
import com.provedcode.sponsor.model.dto.SponsorDTO;
import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.sponsor.repository.SponsorRepository;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Transactional
public class SponsorService {
    PageProperties pageProperties;
    SponsorRepository sponsorRepository;
    UserInfoRepository userInfoRepository;

    @Transactional(readOnly = true)
    public Page<Sponsor> getAllSponsors(Optional<Integer> page, Optional<Integer> size) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        return sponsorRepository.findAll(PageRequest.of(page.orElse(pageProperties.defaultPageNum()),
                size.orElse(pageProperties.defaultPageSize())));
    }

    @Transactional(readOnly = true)
    public Sponsor getSponsorById(long id, Authentication authentication) {
        Sponsor sponsor = sponsorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, String.format("sponsor with id = %d not found", id)));
        UserInfo user = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED, "login is not valid"));

        if (!sponsor.getId().equals(user.getSponsor().getId()))
            throw new ResponseStatusException(FORBIDDEN, "The user cannot view someone else's profile");
        return sponsor;
    }

    public Sponsor editSponsorById(long id, SponsorDTO sponsorDTO, Authentication authentication) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "sponsor with id = %s not found".formatted(id)));
        UserInfo user = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED, "login is not valid"));

        if (!sponsor.getId().equals(user.getSponsor().getId())) {
            throw new ResponseStatusException(FORBIDDEN, "The user cannot view someone else's profile");
        }

        if (sponsorDTO.firstName() != null) {
            sponsor.setFirstName(sponsorDTO.firstName());
        }
        if (sponsorDTO.lastName() != null) {
            sponsor.setLastName(sponsorDTO.lastName());
        }
        if (sponsorDTO.image() != null) {
            sponsor.setImage(sponsorDTO.image());
        }
        return sponsorRepository.save(sponsor);
    }
}