package com.provedcode.user.service.impl;

import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.sponsor.repository.SponsorRepository;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.Role;
import com.provedcode.user.model.dto.SponsorRegistrationDTO;
import com.provedcode.user.model.dto.TalentRegistrationDTO;
import com.provedcode.user.model.entity.Authority;
import com.provedcode.user.model.entity.DeletedUser;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.AuthorityRepository;
import com.provedcode.user.repo.DeletedUserRepository;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.user.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    JwtEncoder jwtEncoder;
    UserInfoRepository userInfoRepository;
    TalentRepository talentRepository;
    SponsorRepository sponsorRepository;
    AuthorityRepository authorityRepository;
    PasswordEncoder passwordEncoder;
    DeletedUserRepository deletedUserRepository;

    @Transactional(readOnly = true)
    public Jwt login(String login, Collection<? extends GrantedAuthority> authorities) {
        UserInfo userInfo = getUserInfo(login);

        Role userRole = userInfo.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "user with login = {%s} is not valid"))
                .getAuthority();

        return generateJwtToken(userRole.equals(Role.TALENT)
                ? userInfo.getTalent().getId() : userInfo.getSponsor().getId(),  login, authorities);
    }

    @Override
    public Jwt register(TalentRegistrationDTO talent) {
        isUserExistCheck(talent.login());


        return registerTalent(talent.login(), talent.password(),
                talent.firstName(), talent.lastName(), talent.specialization());
    }

    @Override
    public Jwt register(SponsorRegistrationDTO sponsor) {
        isUserExistCheck(sponsor.login());

        return registerSponsor(sponsor.login(), sponsor.password(), sponsor.firstName(), sponsor.lastName());
    }

    private Jwt registerTalent(String login, String password,
                               String firstName, String lastName,
                               String specialization) {
        Talent talent = Talent.builder()
                .firstName(firstName)
                .lastName(lastName)
                .specialization(specialization)
                .build();
        talentRepository.save(talent);

        UserInfo userInfo = UserInfo.builder()
                .talent(talent)
                .login(login)
                .password(passwordEncoder.encode(password))
                .authorities(Set.of(authorityRepository.findByAuthority(Role.TALENT).orElseThrow()))
                .isLocked(false)
                .build();
        userInfoRepository.save(userInfo);

        String userLogin = userInfo.getLogin();
        Collection<? extends GrantedAuthority> userAuthorities = userInfo.getAuthorities().stream().map(
                Authority::getAuthority).toList();

        log.info("user with login {%s} was saved, his authorities: %s".formatted(userLogin, userAuthorities));

        return generateJwtToken(talent.getId(),  userLogin, userAuthorities);
    }

    private Jwt registerSponsor(String login, String password,
                                String firstName, String lastName) {
        isUserExistCheck(login);

        Sponsor sponsor = Sponsor.builder()
                .firstName(firstName)
                .amountKudos(0L)
                .lastName(lastName)
                .build();
        sponsorRepository.save(sponsor);

        UserInfo userInfo = UserInfo.builder()
                .sponsor(sponsor)
                .login(login)
                .password(passwordEncoder.encode(password))
                .authorities(Set.of(authorityRepository.findByAuthority(Role.SPONSOR).orElseThrow()))
                .isLocked(false)
                .build();
        userInfoRepository.save(userInfo);

        String userLogin = userInfo.getLogin();
        Collection<? extends GrantedAuthority> userAuthorities = userInfo.getAuthorities().stream()
                .map(Authority::getAuthority).toList();

        log.info("user with login {%s} was saved, his authorities: %s".formatted(userLogin, userAuthorities));

        return generateJwtToken(sponsor.getId(), userLogin, userAuthorities);
    }

    public void activateAccount(String uuid) {
        DeletedUser deletedUser = deletedUserRepository.findByUUID(uuid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        UserInfo user = deletedUser.getDeletedUser();
        user.setIsLocked(false);
        deletedUserRepository.deleteById(deletedUser.getId());
        userInfoRepository.save(user);
    }

    private Jwt generateJwtToken(Long id, String login, Collection<? extends GrantedAuthority> authorities) {
        log.info("=== POST /login === auth.login = {}", login);
        log.info("=== POST /login === auth = {}", authorities);
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(60, MINUTES))
                .subject(login)
                .claim("id", id)
                .claim("scope", authorities.stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" ")))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

    private UserInfo getUserInfo(String name) {
        return userInfoRepository.findByLogin(name)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                        "user with login = %s not found", name)));
    }

    private void isUserExistCheck(String login) {
        if (userInfoRepository.existsByLogin(login)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "user with login = {%s} already exists".formatted(login));
        }
    }

}