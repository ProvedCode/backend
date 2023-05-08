package com.provedcode.kudos.controller;

import com.provedcode.kudos.model.request.SetAmountKudos;
import com.provedcode.kudos.model.response.KudosAmount;
import com.provedcode.kudos.model.response.KudosAmountWithSponsor;
import com.provedcode.kudos.service.KudosService;
import com.provedcode.util.annotations.doc.controller.kudos.GetAmountOfKudosApiDoc;
import com.provedcode.util.annotations.doc.controller.kudos.GetKudosForSponsorApiDoc;
import com.provedcode.util.annotations.doc.controller.kudos.PostAddKudosToProofApiDoc;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v3/")
public class KudosController {
    KudosService kudosService;

    @GetKudosForSponsorApiDoc
    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/sponsors/{sponsor-id}/kudos")
    KudosAmount getKudosForSponsor(@PathVariable("sponsor-id") long sponsorId, Authentication authentication) {
        return kudosService.getKudosForSponsor(sponsorId, authentication);
    }

    @GetAmountOfKudosApiDoc
    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/proofs/{proof-id}/kudos")
    KudosAmountWithSponsor getProofKudos(@PathVariable("proof-id") long proofId, Authentication authentication) {
        return kudosService.getProofKudos(proofId, authentication);
    }

    @PostAddKudosToProofApiDoc
    @PreAuthorize("hasRole('SPONSOR')")
    @PostMapping("/proofs/{proof-id}/kudos")
    void addKudosToProof(@PathVariable("proof-id") long proofId,
                         @RequestBody @Valid Optional<SetAmountKudos> amount,
                         Authentication authentication) {
        kudosService.addKudosToProof(proofId, amount, authentication);
    }
}