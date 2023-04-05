package com.provedcode.talent.service;

import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TalentProofService {
    TalentProofRepository talentProofRepository;

    public List<TalentProof> getAllProofs() {
        return talentProofRepository.findAll();
    }
}
