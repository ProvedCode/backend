package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.service.TalentProofService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
