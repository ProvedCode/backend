package com.provedcode.sponsor.controller;

import com.provedcode.sponsor.mapper.SponsorMapper;
import com.provedcode.sponsor.model.dto.SponsorDTO;
import com.provedcode.sponsor.model.dto.SponsorEditDTO;
import com.provedcode.sponsor.service.SponsorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3")
public class SponsorController {
    SponsorService sponsorService;
    SponsorMapper sponsorMapper;

    @Operation(summary = "Get all sponsors (SponsorDTO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class, subTypes = {SponsorDTO.class}))),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST (parameter: page or size are incorrect)",
                    content = @Content)
    })
    @GetMapping("/sponsors")
    @ResponseStatus(HttpStatus.OK)
    Page<SponsorDTO> getSponsors(@RequestParam(value = "page") Optional<Integer> page,
                                 @RequestParam(value = "size") Optional<Integer> size) {
        return sponsorService.getAllSponsors(page, size).map(sponsorMapper::toDto);
    }

    @Operation(summary = "Get sponsor",
            description = "As a sponsor I want to have an opportunity to see my own profile with information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SponsorDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "501",
                    description = "NOT_IMPLEMENTED (login is not valid)",
                    content = @Content)
    })
    @PreAuthorize("hasRole('SPONSOR')")
    @GetMapping("/sponsors/{id}")
    SponsorDTO getSponsor(@PathVariable("id") long id, Authentication authentication) {
        return sponsorMapper.toDto(sponsorService.getSponsorById(id, authentication));
    }

    @Operation(summary = "Edit information about sponsor",
            description = "As a sponsor I want to have an opportunity to edit my personal profile by adding new information, " +
                    "changing already existing information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SponsorDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to change the sponsor)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(
                    responseCode = "501",
                    description = "NOT_IMPLEMENTED (login is not valid)",
                    content = @Content)
    })
    @PreAuthorize("hasRole('SPONSOR')")
    @PatchMapping("/sponsors/{id}")
    SponsorDTO editSponsor(@PathVariable("id") long id,
                           @RequestBody SponsorEditDTO sponsorEditDTO,
                           Authentication authentication) {
        return sponsorMapper.toDto(sponsorService.editSponsorById(id, sponsorEditDTO, authentication));
    }

    @Operation(summary = "Delete sponsor",
            description = "As a sponsor I want to have an opportunity to delete personal accounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND ",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to delete the talent)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST (incorrect id)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "501",
                    description = "NOT_IMPLEMENTED (login is not valid)",
                    content = @Content),
    })
    @PreAuthorize("hasRole('SPONSOR')")
    @DeleteMapping("/sponsors/{id}")
    void deleteSponsor(@PathVariable("id") long id, Authentication authentication) {
        sponsorService.deleteSponsor(id, authentication);
    }
}