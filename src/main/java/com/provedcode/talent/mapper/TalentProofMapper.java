package com.provedcode.talent.mapper;

import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.entity.TalentProof;
import org.springframework.stereotype.Component;

@Component
public class TalentProofMapper {
    public ProofDTO toProofDTO(TalentProof talentProof) {
        return ProofDTO.builder()
                       .id(talentProof.getTalentId())
                       .proof(talentProof.getProof())
                       .build();
    }
}
