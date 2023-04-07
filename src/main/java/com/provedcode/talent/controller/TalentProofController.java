package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/talents")
public class TalentProofController {
    TalentProofService talentProofService;
    TalentProofMapper talentProofMapper;

    @GetMapping("/proofs")
    Page<ProofDTO> getAllProofs(@RequestParam(value = "page") Optional<Integer> page,
                                @RequestParam(value = "size") Optional<Integer> size,
                                @RequestParam(value = "sortDir") Optional<String> sortDir) {
        return talentProofService.getAllProofsPage(page, size, sortDir).map(talentProofMapper::toProofDTO);
    }

    @PreAuthorize("hasRole('TALENT')")
    @DeleteMapping("/{talent-id}/proofs/{proof-id}")
    SessionInfoDTO deleteProof(@PathVariable(value = "talent-id") long talent_id,
                               @PathVariable(value = "proof-id") long proof_id,
                               Authentication authentication) {
        return talentProofService.deleteProofById(talent_id, proof_id, authentication);
    }
}
