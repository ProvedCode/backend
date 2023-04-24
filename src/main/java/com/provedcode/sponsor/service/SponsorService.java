package com.provedcode.sponsor.service;

import com.provedcode.config.PageProperties;
import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.sponsor.model.dto.SponsorEditDTO;
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

import java.util.List;
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

    public Sponsor editSponsorById(long id, SponsorEditDTO sponsorEditDTO, Authentication authentication) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "sponsor with id = %s not found".formatted(id)));
        UserInfo user = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED, "login is not valid"));
        if (!sponsor.getId().equals(user.getSponsor().getId())) {
            throw new ResponseStatusException(FORBIDDEN, "The user cannot edit someone else's profile");
        }

        if (sponsorEditDTO.firstName() != null) {
            sponsor.setFirstName(sponsorEditDTO.firstName());
        }
        if (sponsorEditDTO.lastName() != null) {
            sponsor.setLastName(sponsorEditDTO.lastName());
        }
        if (sponsorEditDTO.image() != null) {
            sponsor.setImage(sponsorEditDTO.image());
        }
        if (sponsorEditDTO.countOfKudos() != null) {
            if (sponsorEditDTO.countOfKudos() > 0) {
                sponsor.setAmountKudos(sponsor.getAmountKudos() + sponsorEditDTO.countOfKudos());
            } else {
                throw new ResponseStatusException(BAD_REQUEST, "count of kudos must be greater than 0");
            }
        }
        return sponsorRepository.save(sponsor);
    }

    public void deleteSponsor(long id, Authentication authentication) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "sponsor with id = %s not found".formatted(id)));
        UserInfo user = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED, "login is not valid"));
        if (!sponsor.getId().equals(user.getSponsor().getId())) {
            throw new ResponseStatusException(FORBIDDEN, "The user cannot edit someone else's profile");
        }

        List<Kudos> kudosList = sponsor.getKudoses().stream().map(i -> {i.setSponsor(null); return i;}).toList();
        sponsor.setKudoses(kudosList);
        userInfoRepository.delete(user);
    }
}