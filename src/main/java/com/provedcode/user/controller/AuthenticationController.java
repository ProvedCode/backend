package com.provedcode.user.controller;

import com.provedcode.user.model.Role;
import com.provedcode.user.model.dto.JwtToken;
import com.provedcode.user.model.dto.SponsorRegistrationDTO;
import com.provedcode.user.model.dto.TalentRegistrationDTO;
import com.provedcode.user.service.AuthenticationService;
import com.provedcode.util.annotations.doc.controller.user.PostSponsorRegistrationApiDoc;
import com.provedcode.util.annotations.doc.controller.user.PostTalentRegistrationApiDoc;
import com.provedcode.util.annotations.doc.controller.user.PostUserLoginApiDoc;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostUserLoginApiDoc
    @PostMapping("/v2/login")
    JwtToken login(Authentication authentication) {
        return new JwtToken(
                authenticationService.login(authentication.getName(), authentication.getAuthorities())
                .getTokenValue()
        );
    }

    @PostTalentRegistrationApiDoc
    @PostMapping("/v2/talents/register")
    @ResponseStatus(HttpStatus.CREATED)
    JwtToken registerTalent(@RequestBody @Valid TalentRegistrationDTO user) {
        return new JwtToken(
                authenticationService.register(user).getTokenValue()
        );
    }

    @PostSponsorRegistrationApiDoc
    @PostMapping("/v3/sponsors/register")
    @ResponseStatus(HttpStatus.CREATED)
    JwtToken registerSponsor(@RequestBody @Valid SponsorRegistrationDTO user) {
        return new JwtToken(authenticationService.register(user).getTokenValue());
    }

    @GetMapping("/v5/activate")
    void activateAccount(@RequestParam("uuid") String uuid) {
        authenticationService.activateAccount(uuid);
    }
}