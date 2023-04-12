package com.provedcode.user.service.impl;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.Role;
import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.dto.UserInfoDTO;
import com.provedcode.user.model.entity.Authority;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.AuthorityRepository;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.user.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    JwtEncoder jwtEncoder;
    UserInfoRepository userInfoRepository;
    TalentRepository talentEntityRepository;
    AuthorityRepository authorityRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserInfoDTO login(String name, Collection<? extends GrantedAuthority> authorities) {
        Optional<Long> id = userInfoRepository.findByLogin(name).map(userInfo -> userInfo.getTalentId());
        if (id.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with id = %d not found", id));
        }

        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(name);
        if (userInfo.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format("talent with name = %s not found", name));
        }
        Optional<Talent> talent = talentEntityRepository.findById(userInfo.get().getTalentId());

        return UserInfoDTO.builder()
                .token(generateJWTToken(name, authorities))
                .id(talent.get().getId())
                .login(userInfo.get().getLogin())
                .firstName(talent.get().getFirstName())
                .lastName(talent.get().getLastName())
                .image(talent.get().getImage())
                .build();
    }

    @Transactional
    public UserInfoDTO register(RegistrationDTO user) {
        if (userInfoRepository.existsByLogin(user.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("user with login = {%s} already exists", user.login()));
        }
        Talent talent = Talent.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .specialization(user.specialization())
                .build();
        talentEntityRepository.save(talent);

        UserInfo userInfo = UserInfo.builder()
                .talentId(talent.getId())
                .login(user.login())
                .password(passwordEncoder.encode(user.password()))
                .build();
        userInfo.setAuthorities(Set.of(authorityRepository.findByAuthority(Role.TALENT).orElseThrow()));

        userInfoRepository.save(userInfo);

        String userLogin = userInfo.getLogin();
        Collection<? extends GrantedAuthority> userAuthorities = userInfo.getAuthorities().stream().map(
                Authority::getAuthority).toList();

        log.info("user with login {%s} was saved, his authorities: %s".formatted(userLogin, userAuthorities));

        return UserInfoDTO.builder()
                .token(generateJWTToken(userLogin, userAuthorities))
                .id(talent.getId())
                .login(userInfo.getLogin())
                .firstName(talent.getFirstName())
                .lastName(talent.getLastName())
                .image(talent.getImage())
                .build();
    }

    private String generateJWTToken(String name, Collection<? extends GrantedAuthority> authorities) {
        log.info("=== POST /login === auth.name = {}", name);
        log.info("=== POST /login === auth = {}", authorities);
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(60, MINUTES))
                .subject(name)
                .claim("scope", authorities.stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" ")))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
