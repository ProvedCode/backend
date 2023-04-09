package com.provedcode.talent.service.impl;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.entity.*;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.service.TalentService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@AllArgsConstructor
@Transactional
public class TalentServiceImpl implements TalentService {
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    ValidateTalentForCompliance validateTalentForCompliance;


    @Override
    @Transactional(readOnly = true)
    public Page<Talent> getTalentsPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        return talentRepository.findAll(PageRequest.of(page.orElse(pageProperties.defaultPageNum()),
                size.orElse(pageProperties.defaultPageSize())));

    }

    @Override
    @Transactional(readOnly = true)
    public Talent getTalentById(long id) {
        Optional<Talent> talent = talentRepository.findById(id);
        if (talent.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }
        return talent.get();
    }

    @Override
    public Talent editTalent(long id, FullTalentDTO fullTalent, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, id);

        Talent oldTalent = talent.get();
        long oldTalentId = oldTalent.getId();

        TalentDescription oldTalentDescription = oldTalent.getTalentDescription();
        List<TalentTalents> oldTalentTalents = oldTalent.getTalentTalents();
        List<TalentLink> oldTalentLinks = oldTalent.getTalentLinks();
        List<TalentContact> oldTalentContacts = oldTalent.getTalentContacts();
        List<TalentAttachedFile> oldTalentAttachedFile = oldTalent.getTalentAttachedFiles();

        if (oldTalentDescription != null) {
            oldTalentDescription
                    .setAdditionalInfo(fullTalent.additionalInfo())
                    .setBio(fullTalent.bio());
        } else {
            oldTalentDescription = TalentDescription.builder()
                    .talentId(oldTalentId)
                    .additionalInfo(fullTalent.additionalInfo())
                    .bio(fullTalent.bio())
                    .talent(oldTalent)
                    .build();
        }

        oldTalentTalents.clear();
        if (fullTalent.talents() != null) {
            oldTalentTalents.addAll(fullTalent.talents().stream().map(s -> TalentTalents.builder()
                    .talentId(oldTalentId)
                    .talent(oldTalent)
                    .talentName(s)
                    .build()).toList());
        }

        oldTalentLinks.clear();
        if (fullTalent.links() != null) {
            oldTalentLinks.addAll(fullTalent.links().stream().map(l -> TalentLink.builder()
                    .talentId(oldTalentId)
                    .talent(oldTalent)
                    .link(l)
                    .build()).toList());
        }

        oldTalentContacts.clear();
        if (fullTalent.contacts() != null) {
            oldTalentContacts.addAll(fullTalent.contacts().stream().map(s -> TalentContact.builder()
                    .talentId(oldTalentId)
                    .talent(oldTalent)
                    .contact(s)
                    .build()).toList());
        }

        oldTalentAttachedFile.clear();
        if (fullTalent.attachedFiles() != null) {
            oldTalentAttachedFile.addAll(fullTalent.attachedFiles().stream().map(s -> TalentAttachedFile.builder()
                            .talentId(
                                    oldTalentId)
                            .talent(oldTalent)
                            .attachedFile(s)
                            .build())
                    .toList());
        }

        oldTalent.setFirstName(fullTalent.firstName())
                .setLastName(fullTalent.lastName())
                .setSpecialization(fullTalent.specialization())
                .setImage(fullTalent.image())
                .setTalentDescription(oldTalentDescription)
                .setTalentTalents(oldTalentTalents)
                .setTalentLinks(oldTalentLinks)
                .setTalentContacts(oldTalentContacts)
                .setTalentAttachedFiles(oldTalentAttachedFile);

        Talent newTalent = talentRepository.save(oldTalent);

        return newTalent;
    }

    @Override
    public SessionInfoDTO deleteTalentById(long id, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, id);

        userInfoRepository.delete(userInfo.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED)));
        talentRepository.delete(talent.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED)));
        return new SessionInfoDTO("deleted", "null");
    }
}
