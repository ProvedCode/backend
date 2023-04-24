package com.provedcode.kudos.controller;

import com.provedcode.kudos.model.response.KudosAmountWithSponsor;
import com.provedcode.kudos.service.KudosService;
import com.provedcode.kudos.model.response.KudosAmount;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/talent")
public class KudosController {
    KudosService kudosService;

    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/sponsors/{sponsor-id}/kudos")
    KudosAmount getKudosForSponsor(@PathVariable("sponsor-id") long id, Authentication authentication) {
        return kudosService.getKudosForSponsor(id, authentication);
    }

    @PreAuthorize("hasRole('SPONSOR')")
    @PostMapping("/proofs/{proof-id}/kudos/{amount}")
    void addKudosToProof(@PathVariable("proof-id") long id,
                         @PathVariable("amount") Long amount,
                         Authentication authentication) {
        kudosService.addKudosToProof(id, amount, authentication);
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/proofs/{proof-id}/kudos")
    KudosAmountWithSponsor getProofKudos(@PathVariable("proof-id") long id, Authentication authentication) {
        return kudosService.getProofKudos(id, authentication);
    }
}