package com.provedcode.user.controller;

import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.UserInfoDTO;
import com.provedcode.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v2/talents")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserInfoDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "NOT_FOUND (Talent not registered)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content)
    })
    @PostMapping("/login")
    UserInfoDTO login(Authentication authentication) {
        return authenticationService.login(authentication.getName(), authentication.getAuthorities());
    }

    @Operation(summary = "Talent Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "CREATED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserInfoDTO.class))),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT (user with login already exists)",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content),
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserInfoDTO register(@RequestBody @Valid RegistrationDTO user) {
        return authenticationService.register(user);
    }
}