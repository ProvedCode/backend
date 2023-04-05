package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/talents")
public class TalentProofController {
    TalentProofService talentProofService;
    TalentProofMapper talentProofMapper;

    @GetMapping("/proofs")
    List<ProofDTO> getAllProofs() {
        return talentProofService.getAllProofs().stream().map(talentProofMapper::toProofDTO).toList();
    }
}
