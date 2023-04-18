package com.provedcode.kudos;

import com.provedcode.kudos.model.response.KudosAmount;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}