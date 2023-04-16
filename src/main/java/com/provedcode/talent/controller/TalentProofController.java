package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentProofMapper;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.dto.StatusDTO;
import com.provedcode.talent.model.request.AddProof;
import com.provedcode.talent.service.TalentProofService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/talents")
public class TalentProofController {
    TalentProofService talentProofService;
    TalentProofMapper talentProofMapper;

    @Operation(summary = "Get all proofs",
            description = "As a guest I want to see a list of all proofs displayed anonymously")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class, subTypes = {ProofDTO.class}))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST, parameter: page, size or order-by are incorrect",
                    content = @Content)
    })
    @GetMapping("/proofs")
    Page<ProofDTO> getAllProofs(@RequestParam(value = "page") Optional<Integer> page,
                                @RequestParam(value = "size") Optional<Integer> size,
                                @RequestParam(value = "order-by") Optional<String> orderBy,
                                @RequestParam(value = "sort-by", defaultValue = "created") String... sortBy) {
        return talentProofService.getAllProofsPage(page, size, orderBy, sortBy).map(talentProofMapper::toProofDTO);
    }

    @Operation(summary = "Get proof")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProofDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST, parameter: page, proof-id or size are incorrect",
                    content = @Content)
    })
    @GetMapping("/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    ProofDTO getTalentProof(@PathVariable(value = "proof-id") long proofId,
                            Authentication authentication) {
        return talentProofMapper.toProofDTO(talentProofService.getTalentProof(proofId, authentication));
    }

    @Operation(summary = "Get all talent proofs",
            description = "As a talent I want to see all proofs in personal profile page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FullProofDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST, wrong @RequestParam like page, size, order-by, sort-by or incorrect talent-id",
                    content = @Content)
    })
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

    @Operation(summary = "Add proof",
            description = "As a talent I want to have an opportunity to add my personal proof")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProofDTO.class)),
                    headers = {@Header(name = "Location",
                            description = "The URI of the created proof",
                            schema = @Schema(type = "string"))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST, (wrong data to add or incorrect talent-id)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to add the proof)",
                    content = @Content)
    })
    @PostMapping("/{talent-id}/proofs")
    @PreAuthorize("hasRole('TALENT')")
    ResponseEntity<?> addProof(@PathVariable(value = "talent-id") long talentId,
                               @RequestBody @Valid AddProof addProof,
                               Authentication authentication) {
        return talentProofService.addProof(addProof, talentId, authentication);
    }

    @Operation(summary = "Edit information about proof",
            description = "As a talent I want to have an opportunity to edit my personal proofs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProofDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST, (wrong data to edit or incorrect talent-id, proof-id)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to edit the proof)",
                    content = @Content)
    })
    @PatchMapping("/{talent-id}/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    ProofDTO editProof(Authentication authentication,
                       @PathVariable("talent-id") long talentId,
                       @PathVariable("proof-id") long proofId,
                       @RequestBody @Valid ProofDTO proof) {
        return talentProofMapper.toProofDTO(
                talentProofService.editTalentProof(talentId, proofId, proof, authentication));
    }

    @Operation(summary = "Delete proof",
            description = "As a talent I want to have an opportunity to delete my personal proofs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StatusDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST, (incorrect talent-id,proof-id)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to delete the proof or " +
                                  "impossible change proofs status from DRAFT to HIDDEN, it should be PUBLISHED)",
                    content = @Content)
    })
    @DeleteMapping("/{talent-id}/proofs/{proof-id}")
    @PreAuthorize("hasRole('TALENT')")
    StatusDTO deleteProof(@PathVariable(value = "talent-id") long talentId,
                          @PathVariable(value = "proof-id") long proofId,
                          Authentication authentication) {
        return talentProofService.deleteProofById(talentId, proofId, authentication);
    }
}