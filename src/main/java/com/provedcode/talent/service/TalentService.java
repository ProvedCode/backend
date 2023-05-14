package com.provedcode.talent.service;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.dto.SkillIdDTO;
import com.provedcode.talent.model.entity.*;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.talent.repo.SkillsRepository;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.AuthorityRepository;
import com.provedcode.user.repo.UserInfoRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TalentService {
    AuthorityRepository authorityRepository;
    TalentProofRepository talentProofRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    ValidateTalentForCompliance validateTalentForCompliance;
    SkillsRepository skillsRepository;

    @Transactional(readOnly = true)
    public Page<Talent> getTalentsPage(Integer page, Integer size) {
        return talentRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Talent getTalentById(long id) {
        return talentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    public Talent editTalent(long id, EditTalent editTalent, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        validateTalentForCompliance.userVerification(talent, userInfo, id);
        checkEditTalentNull(editTalent);

        Talent editableTalent = talent.get();

        TalentDescription editableTalentDescription = editableTalent.getTalentDescription();
//        List<TalentSkills> editableTalentTalents = editableTalent.getTalentTalents();
        List<TalentLink> editableTalentLinks = editableTalent.getTalentLinks();
        List<TalentContact> editableTalentContacts = editableTalent.getTalentContacts();
        List<TalentAttachedFile> editableTalentAttachedFiles = editableTalent.getTalentAttachedFiles();

        if (editTalent.firstName() != null) {
            editableTalent.setFirstName(editTalent.firstName());
        }
        if (editTalent.lastName() != null) {
            editableTalent.setLastName(editTalent.lastName());
        }
        if (editTalent.specialization() != null) {
            editableTalent.setSpecialization(editTalent.specialization());
        }
        if (editTalent.image() != null) {
            editableTalent.setImage(editTalent.image());
        }
        if (editTalent.additionalInfo() != null || editTalent.bio() != null) {
            if (editableTalentDescription != null) {
                if (editTalent.additionalInfo() != null)
                    editableTalentDescription.setAdditionalInfo(editTalent.additionalInfo());
                if (editTalent.bio() != null)
                    editableTalentDescription.setBio(editTalent.bio());
            } else {
                editableTalentDescription = TalentDescription.builder()
                        .additionalInfo(editTalent.additionalInfo())
                        .bio(editTalent.bio())
                        .talent(editableTalent)
                        .build();
            }
            editableTalent.setTalentDescription(editableTalentDescription);
        }
//        if (editTalent.talents() != null) {
//            editableTalentTalents.clear();
//            editableTalentTalents.addAll(editTalent.talents().stream().map(s -> TalentSkills.builder()
//                                                                                             .talent(editableTalent)
//                                                                                             .talentName(s)
//                                                                                             .build()).toList());
//            editableTalent.setTalentTalents(editableTalentTalents);
//        }
        if (editTalent.links() != null) {
            editableTalentLinks.clear();
            editableTalentLinks.addAll(editTalent.links().stream().map(s -> TalentLink.builder()
                    .talent(editableTalent)
                    .link(s)
                    .build()).toList());
            editableTalent.setTalentLinks(editableTalentLinks);
        }
        if (editTalent.contacts() != null) {
            editableTalentContacts.clear();
            editableTalentContacts.addAll(editTalent.contacts().stream().map(s -> TalentContact.builder()
                    .talent(editableTalent)
                    .contact(s)
                    .build()).toList());
            editableTalent.setTalentContacts(editableTalentContacts);
        }
        if (editTalent.attachedFiles() != null) {
            editableTalentAttachedFiles.clear();
            editableTalentAttachedFiles.addAll(editTalent.attachedFiles().stream().map(s -> TalentAttachedFile.builder()
                            .talent(editableTalent)
                            .attachedFile(
                                    s)
                            .build())
                    .toList());
            editableTalent.setTalentAttachedFiles(editableTalentAttachedFiles);
        }
        return talentRepository.save(editableTalent);
    }

    public SessionInfoDTO deleteTalentById(long id, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, id);

        UserInfo user = userInfo.get();
        Talent entity = talent.get();

        user.getAuthorities().clear();
        userInfoRepository.delete(user);
        talentRepository.delete(entity);

        return new SessionInfoDTO("deleted", "null");
    }

    private void checkEditTalentNull(EditTalent editTalent) {
        if (editTalent.firstName() == null && editTalent.lastName() == null && editTalent.image() == null &&
                editTalent.specialization() == null && editTalent.additionalInfo() == null && editTalent.bio() == null &&
                editTalent.links() == null && editTalent.contacts() == null && editTalent.attachedFiles() == null)
            throw new ResponseStatusException(BAD_REQUEST, "you did not provide information to make changes");
    }

    public void addSkillOnTalent(long id, SkillIdDTO skillIdDTO, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(id);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        validateTalentForCompliance.userVerification(talent, userInfo, id);
        Talent talentObject = talent.get();

        Set<Skills> skillsFromRepo = skillIdDTO.id().stream()
                .map(element -> skillsRepository.findById(element)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                                "Skill with id = %d not found".formatted(element))))
                .collect(Collectors.toSet());

        Set<Skills> skillsFromProofs = talentObject.getTalentProofs().stream()
                .flatMap(talentProof -> talentProof.getSkills().stream())
                .collect(Collectors.toSet());

        skillsFromRepo.stream()
                .filter(skill -> !skillsFromProofs.contains(skill))
                .findFirst()
                .ifPresent(skill -> {
                    throw new ResponseStatusException(FORBIDDEN,
                            "Skill with id = %d not found in talent's proofs".formatted(skill.getId()));
                });

        skillsFromRepo.stream()
                .forEach(skill -> talentObject.getSkills().add(skill));
    }

    public void deleteSkillFromTalent(long talentId, long skillId, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        Skills skill = skillsRepository.findById(skillId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Skill with id = %d not found".formatted(skillId)));
        validateTalentForCompliance.userVerification(talent, userInfo, talentId);
        Talent talentObject = talent.get();
        if (!talentObject.getSkills().remove(skill)) {
            throw new ResponseStatusException(NOT_FOUND,
                    "Skill with id = %d not found".formatted(skillId));
        }
    }

    @Transactional(readOnly = true)
    public Page<Talent> getFilteredBySkillsTalentsPage(@PositiveOrZero Integer page,
                                                       @Min(1) @Max(1000) Integer size,
                                                       String... filterBy) {
        return filterBy != null ?
                talentRepository.findBySkills_SkillsInIgnoreCase(PageRequest.of(page, size), Arrays.stream(filterBy).map(String::toUpperCase).toList())
                : talentRepository.findAll(PageRequest.of(page, size));
    }
}