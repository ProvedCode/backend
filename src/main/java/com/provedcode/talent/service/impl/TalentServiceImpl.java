package com.provedcode.talent.service.impl;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.repo.TalentSkillRepository;
import com.provedcode.talent.service.TalentService;
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
    private final AuthorityRepository authorityRepository;
    TalentMapper talentMapper;
    TalentRepository talentRepository;
    TalentSkillRepository talentSkillRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    TalentRepository talentEntityRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<ShortTalentDTO> getTalentsPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        return talentRepository.findAll(PageRequest.of(page.orElse(pageProperties.defaultPageNum()),
                                                       size.orElse(pageProperties.defaultPageSize())))
                               .map(talentMapper::talentToShortTalentDTO);

    }

    @Override
    @Transactional(readOnly = true)
    public FullTalentDTO getTalentById(long id) {
        Optional<Talent> talent = talentRepository.findById(id);
        if (talent.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }
        return talentMapper.talentToFullTalentDTO(talent.get());
    }

    @Override
    public FullTalentDTO editTalent(long id, FullTalentDTO fullTalent, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        if (talent.isEmpty() || userInfo.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }
        if (userInfo.get().getTalent().getId() != id) {
            throw new ResponseStatusException(FORBIDDEN, "you can`t update another user");
        }

        Talent oldTalent = talent.get();
        long oldTalentId = oldTalent.getId();

        TalentDescription oldTalentDescription = oldTalent.getTalentDescription();
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

        List<TalentSkill> oldTalentSkills = oldTalent.getTalentSkills();
        oldTalentSkills.clear();
        oldTalentSkills.addAll(fullTalent.skills().stream().map(s -> TalentSkill.builder()
                                                                                .talentId(oldTalentId)
                                                                                .talent(oldTalent)
                                                                                .skill(s).build()).toList());

        List<TalentLink> oldTalentLinks = oldTalent.getTalentLinks();
        oldTalentLinks.clear();
        oldTalentLinks.addAll(fullTalent.links().stream().map(l -> TalentLink.builder()
                                                                             .talentId(oldTalentId)
                                                                             .talent(oldTalent)
                                                                             .link(l).build()).toList());

        List<TalentContact> oldTalentContacts = oldTalent.getTalentContacts();
        oldTalentContacts.clear();
        oldTalentContacts.addAll(fullTalent.contacts().stream().map(s -> TalentContact.builder()
                                                                                      .talentId(oldTalentId)
                                                                                      .talent(oldTalent)
                                                                                      .contact(s).build()).toList());
        List<TalentAttachedFile> oldTalentAttachedFile = oldTalent.getTalentAttachedFiles();
        oldTalentAttachedFile.clear();
        oldTalentAttachedFile.addAll(fullTalent.attachedFiles().stream().map(s -> TalentAttachedFile.builder()
                                                                                                    .talentId(
                                                                                                            oldTalentId)
                                                                                                    .talent(oldTalent)
                                                                                                    .attachedFile(s)
                                                                                                    .build()).toList());

        oldTalent.setFirstName(fullTalent.firstName())
                 .setLastName(fullTalent.lastName())
                 .setSpecialization(fullTalent.specialization())
                 .setImage(fullTalent.image())
                 .setTalentDescription(oldTalentDescription)
                 .setTalentSkills(oldTalentSkills)
                 .setTalentLinks(oldTalentLinks)
                 .setTalentContacts(oldTalentContacts)
                 .setTalentAttachedFiles(oldTalentAttachedFile);

        talentRepository.save(oldTalent);

        return talentMapper.talentToFullTalentDTO(oldTalent);
    }

    @Override
    public SessionInfoDTO deleteTalentById(long id, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        if (talent.isEmpty() || userInfo.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }
        if (userInfo.get().getTalent().getId() != id) {
            throw new ResponseStatusException(FORBIDDEN, "you can`t delete another user");
        }

        userInfoRepository.delete(userInfo.get());
        talentRepository.deleteById(id);
        return new SessionInfoDTO("deleted", "null");
    }

//    public void userVerification(Optional<Talent> talent, Optional<UserInfo> userInfo, long id) {
//        if (talent.isEmpty()) {
//            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
//        }
//        if (userInfo.get().getTalent().getId() != id) {
//            throw new ResponseStatusException(UNAUTHORIZED);
//        }
//    }
}
