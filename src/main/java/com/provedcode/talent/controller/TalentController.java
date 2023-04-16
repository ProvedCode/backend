package com.provedcode.talent.controller;

import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.talent.service.TalentService;
import com.provedcode.user.model.dto.SessionInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "talent", description = "Talent API")
public class TalentController {
    TalentService talentService;
    TalentMapper talentMapper;

    @Operation(summary = "Get all talents",
            description = "As a guest I want to see a page with a list of all “talents” cards displayed with a short description about them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class, subTypes = {ShortTalentDTO.class}))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST (parameter: page or size are incorrect)",
                    content = @Content)
    })
    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    Page<ShortTalentDTO> getTalents(@RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size) {
        return talentService.getTalentsPage(page, size).map(talentMapper::talentToShortTalentDTO);
    }

    @Operation(summary = "Get talent",
            description = "As a talent I want to have an opportunity to see the full information about the talent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FullTalentDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
    })
    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/talents/{id}")
    FullTalentDTO getTalent(@PathVariable("id") long id, Authentication authentication) {
        log.info("get-talent auth = {}", authentication);
        log.info("get-talent auth.name = {}", authentication.getAuthorities());
        return talentMapper.talentToFullTalentDTO(talentService.getTalentById(id));
    }

    @Operation(summary = "Edit information about talent",
            description = "As a talent I want to have an opportunity to edit my personal profile by adding new information, changing already existing information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FullTalentDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (if not the owner wants to change the talent)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content)
    })
    @PreAuthorize("hasRole('TALENT')")
    @PatchMapping("/talents/{talent-id}")
    FullTalentDTO editTalent(@PathVariable("talent-id") long id,
                             @RequestBody @Valid EditTalent editTalent,
                             Authentication authentication) {
        return talentMapper.talentToFullTalentDTO(talentService.editTalent(id, editTalent, authentication));
    }

    @Operation(summary = "Delete talent",
            description = "As a talent I want to have an opportunity to delete personal accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FullTalentDTO.class))),
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
                    content = @Content)
    })
    @PreAuthorize("hasRole('TALENT')")
    @DeleteMapping("/talents/{id}")
    SessionInfoDTO deleteTalent(@PathVariable("id") long id, Authentication authentication) {
        return talentService.deleteTalentById(id, authentication);
    }
}