package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.ProofSkillsDTO;
import com.provedcode.talent.model.dto.SkillsOnProofDTO;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public interface ProofSkillsService {
    void addSkillsOnProof(long talentId, long proofId, ProofSkillsDTO skills, Authentication authentication);

    @Transactional(readOnly = true)
    SkillsOnProofDTO getAllSkillsOnProof(long proofId, Authentication authentication);

    void deleteSkillOnProof(long talentId, long proofId, long skillId, Authentication authentication);
}
