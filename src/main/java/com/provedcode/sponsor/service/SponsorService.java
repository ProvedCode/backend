package com.provedcode.sponsor.service;

import com.provedcode.config.PageProperties;
import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.sponsor.model.request.EditSponsor;
import com.provedcode.sponsor.repository.SponsorRepository;
import com.provedcode.sponsor.utill.ValidateSponsorForCompliance;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@AllArgsConstructor
@Transactional
public class SponsorService {
    PageProperties pageProperties;
    SponsorRepository sponsorRepository;
    UserInfoRepository userInfoRepository;
    ValidateSponsorForCompliance validateSponsorForCompliance;

    @Transactional(readOnly = true)
    public Page<Sponsor> getAllSponsors(Integer page, Integer size) {
        return sponsorRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Sponsor getSponsorById(long id, Authentication authentication) {
        Optional<UserInfo> user = userInfoRepository.findByLogin(authentication.getName());
        Optional<Sponsor> sponsor = sponsorRepository.findById(id);
        validateSponsorForCompliance.userVerification(sponsor, user, id);
        return sponsor.get();
    }

    public Sponsor editSponsorById(long id, EditSponsor editSponsor, Authentication authentication) {
        Optional<UserInfo> user = userInfoRepository.findByLogin(authentication.getName());
        Optional<Sponsor> sponsor = sponsorRepository.findById(id);
        validateSponsorForCompliance.userVerification(sponsor, user, id);
        checkEditSponsorNull(editSponsor);

        Sponsor editableSponsor = sponsor.get();
        if (editSponsor.firstName() != null) {
            editableSponsor.setFirstName(editSponsor.firstName());
        }
        if (editSponsor.lastName() != null) {
            editableSponsor.setLastName(editSponsor.lastName());
        }
        if (editSponsor.image() != null) {
            editableSponsor.setImage(editSponsor.image());
        }
        if (editSponsor.countOfKudos() != null) {
            if (editSponsor.countOfKudos() > 0) {
                editableSponsor.setAmountKudos(editableSponsor.getAmountKudos() + editSponsor.countOfKudos());
            } else {
                throw new ResponseStatusException(BAD_REQUEST, "count of kudos must be greater than 0");
            }
        }
        return sponsorRepository.save(editableSponsor);
    }

    public void deleteSponsor(long id, Authentication authentication) {
        Optional<UserInfo> user = userInfoRepository.findByLogin(authentication.getName());
        Optional<Sponsor> sponsor = sponsorRepository.findById(id);
        validateSponsorForCompliance.userVerification(sponsor, user, id);

        Sponsor deletableSponsor = sponsor.get();
        List<Kudos> kudosList = deletableSponsor.getKudoses().stream().map(i -> {
            i.setSponsor(null);
            return i;
        }).toList();
        deletableSponsor.setKudoses(kudosList);
        userInfoRepository.delete(user.get());
    }

    private void checkEditSponsorNull(EditSponsor editSponsor) {
        if (editSponsor.firstName() == null && editSponsor.lastName() == null && editSponsor.image() == null &&
            editSponsor.countOfKudos() == null)
            throw new ResponseStatusException(FORBIDDEN, "you did not provide information to make changes");
    }
}