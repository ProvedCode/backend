package com.provedcode.kudos.controller;

import com.provedcode.kudos.model.response.KudosAmountWithSponsor;
import com.provedcode.kudos.service.KudosService;
import com.provedcode.kudos.model.response.KudosAmount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/")
public class KudosController {
    KudosService kudosService;

    @Operation(summary = "Get all available kudos from a sponsor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = KudosAmount.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to see other sponsor kudos)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content)
    })
    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/sponsors/{sponsor-id}/kudos")
    KudosAmount getKudosForSponsor(@PathVariable("sponsor-id") long id, Authentication authentication) {
        return kudosService.getKudosForSponsor(id, authentication);
    }

    @Operation(summary = "As a sponsor I want to estimate talent proof by giving kudos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if sponsor does not have enough kudos)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content)
    })
    @PreAuthorize("hasRole('SPONSOR')")
    @PostMapping("/proofs/{proof-id}/kudos/{amount}")
    void addKudosToProof(@PathVariable("proof-id") long id,
                         @PathVariable("amount") Long amount,
                         Authentication authentication) {
        kudosService.addKudosToProof(id, amount, authentication);
    }

    @Operation(summary = "Amount of “kudos” given by sponsors and who gave the “kudos” on proof")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = KudosAmountWithSponsor.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the talent wants to see)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content)
    })
    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/proofs/{proof-id}/kudos")
    KudosAmountWithSponsor getProofKudos(@PathVariable("proof-id") long id, Authentication authentication) {
        return kudosService.getProofKudos(id, authentication);
    }
}