package com.provedcode.talent.controller;

import com.provedcode.talent.model.dto.ProofSkillsDTO;
import com.provedcode.talent.model.dto.SkillsOnProofDTO;
import com.provedcode.talent.service.ProofSkillsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@AllArgsConstructor

@RestController
@RequestMapping("/api/v4/talents")
public class TalentSkillsController {
    ProofSkillsService talentSkillsService;

//    @PostMapping("/{talent-id}/proofs/{proof-id}/skills")
//    void addSkillOnProof(@PathVariable("talent-id") long talentId,
//                         @PathVariable("proof-id") long proofId,
//                         @RequestBody @Valid ProofSkillsDTO skills,
//                         Authentication authentication) {
//        talentSkillsService.addSkillsOnProof(talentId, proofId, skills, authentication);
//    }
//
//    @GetMapping("/{talent-id}/proofs/{proof-id}/skills")
//    SkillsOnProofDTO getAllSkillsOnProof(@PathVariable("talent-id") long talentId,
//                                         @PathVariable("proof-id") long proofId,
//                                         Authentication authentication) {
//        return talentSkillsService.getAllSkillsOnProof(talentId, proofId, authentication);
//    }
//    @PreAuthorize("hasRole('TALENT')")
//    @DeleteMapping("/{talent-id}/proofs/{proof-id}/skills/{skill-id}")
//    void deleteSkillOnProof(@PathVariable("talent-id") long talentId,
//                            @PathVariable("proof-id") long proofId,
//                            @PathVariable("skill-id") long skillId,
//                            Authentication authentication) {
//        talentSkillsService.deleteSkillOnProof(talentId, proofId, skillId, authentication);
//    }
}
