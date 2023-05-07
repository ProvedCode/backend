package com.provedcode.talent.service;

import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.ProofSkillsDTO;
import com.provedcode.talent.model.dto.SkillsOnProofDTO;
import com.provedcode.talent.model.entity.Skills;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.SkillsRepository;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import static org.springframework.http.HttpStatus.*;

@Transactional
@Service
@AllArgsConstructor
public class TalentSkillsService {
    SkillsRepository skillsRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    TalentProofRepository talentProofRepository;

    static BiConsumer<Long, UserInfo> isValidUserEditTalent = (talentId, userInfo) -> {
        if (!userInfo.getTalent().getId().equals(talentId)) {
            throw new ResponseStatusException(CONFLICT, "you can`t change another talent");
        }
    };

    public void addSkillsOnProof(long talentId, long proofId, ProofSkillsDTO skills, Authentication authentication) {
        if (!talentRepository.existsById(talentId)) {
            throw new ResponseStatusException(NOT_FOUND, "talent with id = %s not found".formatted(talentId));
        }
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        TalentProof talentProof = talentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "proof with id = %s not found".formatted(proofId)));
        if (!talentProof.getStatus().equals(ProofStatus.DRAFT)) {
            throw new ResponseStatusException(CONFLICT, "proof status must be DRAFT");
        }

        isValidUserEditTalent.accept(talentId, userInfo);
        if (skills.skills().stream().anyMatch(i -> !skillsRepository.existsById(i))) {
            throw new ResponseStatusException(BAD_REQUEST, "no such skill with id");
        }

        Set<Skills> skillsSet = new HashSet<>(skillsRepository.findAllById(skills.skills()));

        talentProof.getSkills().addAll(skillsSet);
        talentProofRepository.save(talentProof);
    }

    @Transactional(readOnly = true)
    public SkillsOnProofDTO getAllSkillsOnProof(long talentId, long proofId, Authentication authentication) {
        TalentProof talentProof = talentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "proof with id = %s not found".formatted(proofId)));
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "talent with id = %s not found".formatted(talentId)));
        if (!talent.getId().equals(talentProof.getTalent().getId())) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "talentId with id = %s and proofId with id = %s do not match".formatted(talentId, proofId));
        }

        if (talentProof.getStatus().equals(ProofStatus.PUBLISHED)) {
            return SkillsOnProofDTO.builder().skills(talentProof.getSkills()).build();
        } else if (authentication != null) {
            UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
            if (userInfo.getTalent().getId().equals(talentProof.getTalent().getId())) {
                return SkillsOnProofDTO.builder().skills(talentProof.getSkills()).build();
            } else {
                throw new ResponseStatusException(FORBIDDEN, "you can't see proofs in DRAFT and HIDDEN status");
            }
        } else {
            throw new ResponseStatusException(FORBIDDEN, "you can't see proofs in DRAFT and HIDDEN status");
        }
    }

    public void deleteSkillOnProof(long talentId, long proofId, long skillId, Authentication authentication) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "talent with id = %s not found".formatted(talentId)));
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        TalentProof talentProof = talentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "proof with id = %s not found".formatted(proofId)));
        if (!talentProof.getStatus().equals(ProofStatus.DRAFT)) {
            throw new ResponseStatusException(CONFLICT, "proof status must be DRAFT");
        }
        if (!talent.getId().equals(talentProof.getTalent().getId())) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "talentId with id = %s and proofId with id = %s do not match".formatted(talentId, proofId));
        }
        isValidUserEditTalent.accept(talentId, userInfo);
        Skills skills = skillsRepository.findById(skillId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "skill with id = %s not found".formatted(skillId)));
        if (!talentProof.getSkills().contains(skills)) {
            throw new ResponseStatusException(NOT_FOUND,
                    "you dont have skill with id = %s on proof with id = %s".formatted(skillId, proofId));
        }
        talentProof.getSkills().remove(skills);
    }
}
