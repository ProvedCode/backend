package com.provedcode.talent.service;

import com.provedcode.talent.mapper.SkillMapper;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.ProofSkillsDTO;
import com.provedcode.talent.model.dto.SkillDTO;
import com.provedcode.talent.model.dto.SkillsOnProofDTO;
import com.provedcode.talent.model.entity.Skill;
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
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Transactional
@Service
@AllArgsConstructor
public class ProofSkillsService {
    SkillsRepository skillsRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    TalentProofRepository talentProofRepository;
    SkillMapper skillMapper;


    static BiConsumer<Long, UserInfo> isValidUserEditTalent = (talentId, userInfo) -> {
        if (!userInfo.getTalent().getId().equals(talentId)) {
            throw new ResponseStatusException(CONFLICT, "you can`t change another talent");
        }
    };

//    public void addSkillsOnProof(long talentId, long proofId, ProofSkillsDTO skills, Authentication authentication) {
//        if (!talentRepository.existsById(talentId)) {
//            throw new ResponseStatusException(NOT_FOUND, "talent with id = %s not found".formatted(talentId));
//        }
//        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//        TalentProof talentProof = talentProofRepository.findById(proofId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "proof with id = %s not found".formatted(proofId)));
//        if (!talentProof.getStatus().equals(ProofStatus.DRAFT)) {
//            throw new ResponseStatusException(CONFLICT, "proof status must be DRAFT");
//        }
//
//        isValidUserEditTalent.accept(talentId, userInfo);
//        skills.skills().forEach(skillId -> {
//            if (!skillsRepository.existsById(skillId))
//                throw new ResponseStatusException(NOT_FOUND, "no such skill with id = " + skillId);
//        });
//
//        Set<Skills> skillsSet = new HashSet<>(skillsRepository.findAllById(skills.skills()));
//        talentProof.getSkills().forEach(skill -> {
//            if (skillsSet.contains(skill))
//                throw new ResponseStatusException(CONFLICT,
//                        "skill with id = %s already on skill".formatted(skill.getId()));
//        });
//
//        talentProof.getSkills().addAll(skillsSet);
//        talentProofRepository.save(talentProof);
//    }

//    @Transactional(readOnly = true)
//    public SkillsOnProofDTO getAllSkillsOnProof(long talentId, long proofId, Authentication authentication) {
//        TalentProof talentProof = talentProofRepository.findById(proofId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
//                        "proof with id = %s not found".formatted(proofId)));
//        Talent talent = talentRepository.findById(talentId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
//                        "talent with id = %s not found".formatted(talentId)));
//        if (!talent.getId().equals(talentProof.getTalent().getId())) {
//            throw new ResponseStatusException(BAD_REQUEST,
//                    "talentId with id = %s and proofId with id = %s do not match".formatted(talentId, proofId));
//        }
//        Set<SkillDTO> skills = talentProof.getSkills().stream()
//                .map(skillMapper::skillToSkillDTO).collect(Collectors.toSet());
//        if (talentProof.getStatus().equals(ProofStatus.PUBLISHED)) {
//            return SkillsOnProofDTO.builder().skills(skills).build();
//        } else if (authentication != null) {
//            UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
//                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//            if (userInfo.getTalent().getId().equals(talentProof.getTalent().getId())) {
//                return SkillsOnProofDTO.builder().skills(skills).build();
//            } else {
//                throw new ResponseStatusException(FORBIDDEN, "you can't see proofs in DRAFT and HIDDEN status");
//            }
//        } else {
//            throw new ResponseStatusException(FORBIDDEN, "you can't see proofs in DRAFT and HIDDEN status");
//        }
//    }

//    public void deleteSkillOnProof(long talentId, long proofId, long skillId, Authentication authentication) {
//        Talent talent = talentRepository.findById(talentId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
//                        "talent with id = %s not found".formatted(talentId)));
//        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//        TalentProof talentProof = talentProofRepository.findById(proofId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "proof with id = %s not found".formatted(proofId)));
//        if (!talentProof.getStatus().equals(ProofStatus.DRAFT)) {
//            throw new ResponseStatusException(CONFLICT, "proof status must be DRAFT");
//        }
//        if (!talent.getId().equals(talentProof.getTalent().getId())) {
//            throw new ResponseStatusException(BAD_REQUEST,
//                    "talentId with id = %s and proofId with id = %s do not match".formatted(talentId, proofId));
//        }
//        isValidUserEditTalent.accept(talentId, userInfo);
//        Skills skills = skillsRepository.findById(skillId)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
//                        "skill with id = %s not found".formatted(skillId)));
//        if (!talentProof.getSkills().contains(skills)) {
//            throw new ResponseStatusException(NOT_FOUND,
//                    "you dont have skill with id = %s on proof with id = %s".formatted(skillId, proofId));
//        }
//        talentProof.getSkills().remove(skills);
//    }
}
