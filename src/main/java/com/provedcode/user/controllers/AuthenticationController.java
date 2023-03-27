package com.provedcode.user.controllers;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.db.TalentEntityRepository;
import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserAuthorityRepository;
import com.provedcode.user.repo.UserInfoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AuthenticationController {
    JwtEncoder jwtEncoder;
    UserInfoRepository userInfoRepository;
    TalentEntityRepository talentEntityRepository;
    UserAuthorityRepository userAuthorityRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    String login(Authentication authentication) {
        log.info("=== POST /login === auth.name = {}", authentication.getName());
        log.info("=== POST /login === auth = {}", authentication);
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, MINUTES))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @PostMapping("/register")
    String register(@Valid RegistrationDTO user) {
        if (userInfoRepository.existsByLogin(user.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user with login = {%s} already exists", user.login()));
        }
        Talent talent = talentEntityRepository.save(Talent.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .specialization(user.specialization())
                .build());
        userInfoRepository.save(
                UserInfo.builder()
                        .talent(talent)
                        .userId(talent.getId())
                        .userAuthorities(Set.of(userAuthorityRepository.findByAuthority_Authority("ROLE_USER")
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user didn't created"))))
                        .login(user.login())
                        .password(passwordEncoder.encode(user.password()))
                        .build()
        );
        return "YAY";
    }


    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
