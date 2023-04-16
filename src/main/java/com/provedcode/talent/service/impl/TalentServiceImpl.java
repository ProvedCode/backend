package com.provedcode.talent.service.impl;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.entity.*;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.service.TalentService;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.AuthorityRepository;
import com.provedcode.user.repo.UserInfoRepository;
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
    AuthorityRepository authorityRepository;
    TalentProofRepository talentProofRepository;
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
    public Talent editTalent(long id, EditTalent editTalent, Authentication authentication) {
        if (editTalent.firstName() == null && editTalent.lastName() == null && editTalent.image() == null &&
            editTalent.specialization() == null && editTalent.additionalInfo() == null && editTalent.bio() == null &&
            editTalent.talents() == null && editTalent.links() == null && editTalent.contacts() == null &&
            editTalent.attachedFiles() == null)
            throw new ResponseStatusException(BAD_REQUEST, "you did not provide information to make changes");

        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, id);

        Talent editableTalent = talent.get();
        long idEditableTalent = editableTalent.getId();

        TalentDescription editableTalentDescription = editableTalent.getTalentDescription();
        List<TalentTalents> editableTalentTalents = editableTalent.getTalentTalents();
        List<TalentLink> editableTalentLinks = editableTalent.getTalentLinks();
        List<TalentContact> editableTalentContacts = editableTalent.getTalentContacts();
        List<TalentAttachedFile> editableTalentAttachedFile = editableTalent.getTalentAttachedFiles();

        if (editableTalentDescription != null) {
            editableTalentDescription
                    .setAdditionalInfo(editTalent.additionalInfo() != null ? editTalent.additionalInfo()
                                                                           : editableTalentDescription.getAdditionalInfo())
                    .setBio(editTalent.bio() != null ? editTalent.bio() : editableTalentDescription.getBio());
        } else {
            editableTalentDescription = TalentDescription.builder()
                                                         .talentId(idEditableTalent)
                                                         .additionalInfo(editTalent.additionalInfo())
                                                         .bio(editTalent.bio())
                                                         .talent(editableTalent)
                                                         .build();
        }

        if (editTalent.talents() != null) {
            editableTalentTalents.clear();
            editableTalentTalents.addAll(editTalent.talents().stream().map(s -> TalentTalents.builder()
                                                                                             .talentId(idEditableTalent)
                                                                                             .talent(editableTalent)
                                                                                             .talentName(s)
                                                                                             .build()).toList());
        }

        if (editTalent.links() != null) {
            editableTalentLinks.clear();
            editableTalentLinks.addAll(editTalent.links().stream().map(l -> TalentLink.builder()
                                                                                      .talentId(idEditableTalent)
                                                                                      .talent(editableTalent)
                                                                                      .link(l)
                                                                                      .build()).toList());
        }

        if (editTalent.contacts() != null) {
            editableTalentContacts.clear();
            editableTalentContacts.addAll(editTalent.contacts().stream().map(s -> TalentContact.builder()
                                                                                               .talentId(
                                                                                                       idEditableTalent)
                                                                                               .talent(editableTalent)
                                                                                               .contact(s)
                                                                                               .build()).toList());
        }

        if (editTalent.attachedFiles() != null) {
            editableTalentAttachedFile.clear();
            editableTalentAttachedFile.addAll(editTalent.attachedFiles().stream().map(s -> TalentAttachedFile.builder()
                                                                                                             .talentId(
                                                                                                                     idEditableTalent)
                                                                                                             .talent(editableTalent)
                                                                                                             .attachedFile(
                                                                                                                     s)
                                                                                                             .build())
                                                        .toList());
        }

        editableTalent.setFirstName(
                              editTalent.firstName() != null ? editTalent.firstName() : editableTalent.getFirstName())
                      .setLastName(editTalent.lastName() != null ? editTalent.lastName() : editableTalent.getLastName())
                      .setSpecialization(editTalent.specialization() != null ? editTalent.specialization()
                                                                             : editableTalent.getSpecialization())
                      .setImage(editTalent.image() != null ? editTalent.image() : editableTalent.getImage())
                      .setTalentDescription(editableTalentDescription)
                      .setTalentTalents(editableTalentTalents)
                      .setTalentLinks(editableTalentLinks)
                      .setTalentContacts(editableTalentContacts)
                      .setTalentAttachedFiles(editableTalentAttachedFile);

        return talentRepository.save(editableTalent);
    }

    @Override
    public SessionInfoDTO deleteTalentById(long id, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, id);

        UserInfo user = userInfo.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED));
        Talent entity = talent.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED));

        user.getAuthorities().clear();
        userInfoRepository.delete(user);
        talentRepository.delete(entity);

        return new SessionInfoDTO("deleted", "null");
    }
}