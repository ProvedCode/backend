package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import jakarta.validation.Valid;
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
                                @RequestParam(value = "size") Optional<Integer> size) {
        return talentProofService.getAllProofsPage(page, size).map(talentProofMapper::toProofDTO);
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

    @PatchMapping("/{talent-id}/proofs/{id}")
    @PreAuthorize("hasRole('TALENT')")
    ProofDTO editProof(Authentication authentication,
                       @PathVariable("talent-id") long talentId,
                       @PathVariable("id") long id,
                       @RequestBody @Valid ProofDTO proof) {
        return talentProofMapper.toProofDTO(talentProofService.editTalentProof(talentId, id, proof, authentication));
    }
}