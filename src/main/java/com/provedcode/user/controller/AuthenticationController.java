package com.provedcode.user.controller;

import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.UserInfoDTO;
import com.provedcode.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/talents")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    UserInfoDTO login(Authentication authentication) {
        return authenticationService.login(authentication.getName(), authentication.getAuthorities());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserInfoDTO register(@RequestBody @Valid RegistrationDTO user) {
        return authenticationService.register(user);
    }

}
