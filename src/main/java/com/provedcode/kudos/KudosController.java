package com.provedcode.kudos;

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

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/proofs/{proof-id}/kudos")
    KudosAmount getKudosProof(@PathVariable("proof-id") long id) {
        return kudosService.getAmountKudosProof(id);
    }

    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("/proofs/{proof-id}/kudos")
    void addKudosToProof(@PathVariable("proof-id") long id, Authentication authentication) {
        kudosService.addKudosToProof(id, authentication);
    }
}