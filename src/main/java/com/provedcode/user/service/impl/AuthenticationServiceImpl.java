package com.provedcode.user.service.impl;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.db.TalentEntityRepository;
import com.provedcode.user.model.Role;
import com.provedcode.user.model.dto.RegistrationDTO;
import com.provedcode.user.model.entity.UserAuthority;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.AuthorityRepository;
import com.provedcode.user.repo.UserAuthorityRepository;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.user.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    JwtEncoder jwtEncoder;
    UserInfoRepository userInfoRepository;
    TalentEntityRepository talentEntityRepository;
    UserAuthorityRepository userAuthorityRepository;
    AuthorityRepository authorityRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public String login(String name, Collection<? extends GrantedAuthority> authorities) {
        log.info("=== POST /login === auth.name = {}", name);
        log.info("=== POST /login === auth = {}", authorities);
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, MINUTES))
                .subject(name)
                .claim("scope", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Transactional
    public String register(RegistrationDTO user) {
        if (userInfoRepository.existsByLogin(user.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("user with login = {%s} already exists", user.login()));
        }
        Talent talent = talentEntityRepository.save(
                Talent.builder()
                        .firstName(user.firstName())
                        .lastName(user.lastName())
                        .specialization(user.specialization())
                        .build()
        );
        UserInfo userInfo = UserInfo.builder()
                .userId(talent.getId())
                .login(user.login())
                .password(passwordEncoder.encode(user.password()))
                .build();
        UserAuthority userAuthority = UserAuthority.builder()
                .userInfo(userInfo)
                .authority(authorityRepository.findByAuthority(Role.TALENT.toString())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "this authority does`t exist")))
                .build();
        userInfo.setUserAuthorities(Set.of(userAuthority));
        userAuthority.setUserInfo(userInfoRepository.save(userInfo));
        userAuthorityRepository.save(userAuthority);

        String userLogin = userInfo.getLogin();
        Collection<? extends GrantedAuthority> userAuthorities = userInfo.getUserAuthorities().stream().map(i -> new SimpleGrantedAuthority(i.getAuthority().getAuthority())).toList();

        log.info("user with login {%s} was saved, his authorities: %s".formatted(userLogin, userAuthorities));

        return login(userLogin, userAuthorities);
    }

}
