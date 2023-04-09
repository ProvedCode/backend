package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.AddProofDTO;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
                                @RequestParam(value = "order-by") Optional<String> orderBy) {
        return talentProofService.getAllProofsPage(page, size, orderBy).map(talentProofMapper::toProofDTO);
    }

    @GetMapping("/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    ProofDTO getTalentProof(@PathVariable(value = "proof-id") long proofId,
                            Authentication authentication) {
        return talentProofMapper.toProofDTO(talentProofService.getTalentProof(proofId, authentication));
    }

    @GetMapping("/{talent-id}/proofs")
    @PreAuthorize("hasRole('TALENT')")
    FullProofDTO getTalentInformationWithProofs(Authentication authentication,
                                                @PathVariable("talent-id") Long talentId,
                                                @RequestParam(value = "page") Optional<Integer> page,
                                                @RequestParam(value = "size") Optional<Integer> size,
                                                @RequestParam(value = "order-by") Optional<String> orderBy,
                                                @RequestParam(value = "sort-by", defaultValue = "created") String... sortBy) {
        return talentProofService.getTalentProofs(talentId, page, size, orderBy, authentication, sortBy);
    }

    @PostMapping("/{talent-id}/proofs")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<?> addProof(@PathVariable(value = "talent-id") long talentId,
                               @RequestBody AddProofDTO addProofDTO,
                               Authentication authentication) {
        return talentProofService.addProof(addProofDTO, talentId, authentication);
    }

    @PatchMapping("/{talent-id}/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    ProofDTO editProof(Authentication authentication,
                       @PathVariable("talent-id") long talentId,
                       @PathVariable("proof-id") long proofId,
                       @RequestBody @Valid ProofDTO proof) {
        return talentProofMapper.toProofDTO(
                talentProofService.editTalentProof(talentId, proofId, proof, authentication));
    }

    @DeleteMapping("/{talent-id}/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    SessionInfoDTO deleteProof(@PathVariable(value = "talent-id") long talentId,
                               @PathVariable(value = "proof-id") long proofId,
                               Authentication authentication) {
        return talentProofService.deleteProofById(talentId, proofId, authentication);
    }
}