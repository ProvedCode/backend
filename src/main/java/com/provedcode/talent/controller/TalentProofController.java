package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.AddProofDTO;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/{talent-id}/proofs")
    ResponseEntity<?> addProof(@PathVariable(value = "talent-id") long talentId,
                               @RequestBody AddProofDTO addProofDTO,
                               Authentication authentication) {
        return talentProofService.addProof(addProofDTO, talentId, authentication);
    }

    @GetMapping("/{talent-id}/proofs")
    @PreAuthorize("hasRole('TALENT')")
    FullProofDTO getTalentInformationWithProofs(Authentication authentication,
                                                @PathVariable("talent-id") Long talentId,
                                                @RequestParam(value = "page") Optional<Integer> page,
                                                @RequestParam(value = "size") Optional<Integer> size,
                                                @RequestParam(value = "direction") Optional<String> direction,
                                                @RequestParam(value = "sort", defaultValue = "created") String... sort) {
        return talentProofService.getTalentProofs(talentId, page, size, direction, authentication, sort);
    }
}
