package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.model.request.AddProof;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

public interface TalentProofService {
    @Transactional(readOnly = true)
    Page<TalentProof> getAllProofsPage(Integer page, Integer size, String orderBy,
                                       String... sortBy);

    @Transactional(readOnly = true)
    TalentProof getTalentProof(long proofId, Authentication authentication);

    @Transactional(readOnly = true)
    FullProofDTO getTalentProofs(Long talentId, Integer page, Integer size, String direction,
                                 Authentication authentication, String... sortProperties);

    ResponseEntity<?> addProof(AddProof addProof, long talentId, Authentication authentication);

    TalentProof editTalentProof(long talentId, long proofId, ProofDTO proof, Authentication authentication);

    void deleteProofById(long talentId, long proofId, Authentication authentication);
}
