package com.provedcode.talent.service;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.entity.ProofSkill;
import com.provedcode.talent.model.entity.Skill;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.model.request.AddProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TalentProofService {
    private final TalentProofRepository talentProofRepository;
    private final TalentRepository talentRepository;
    private final UserInfoRepository userInfoRepository;
    private final PageProperties pageProperties;
    private final ValidateTalentForCompliance validateTalentForCompliance;

    @Transactional(readOnly = true)
    public Page<TalentProof> getAllProofsPage(Integer page, Integer size, String orderBy, String... sortBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.valueOf(orderBy.toUpperCase()),
                sortBy
        );
        return talentProofRepository.findByStatus(ProofStatus.PUBLISHED, pageRequest);
    }

    @Transactional(readOnly = true)
    public TalentProof getTalentProof(long proofId, Authentication authentication) {
        TalentProof talentProof = getTalentProof(proofId);
        UserInfo userInfo = getUserInfo(authentication.getName());

        if (talentProof.getTalent().getId().equals(userInfo.getTalent().getId())
                || talentProof.getStatus().equals(ProofStatus.PUBLISHED)) {
            return talentProof;
        } else {
            throw new ResponseStatusException(FORBIDDEN);
        }
    }

    @Transactional(readOnly = true)
    public FullProofDTO getTalentProofs(Long talentId, Integer page, Integer size, String direction, Authentication authentication, String... sortProperties) {
        Talent talent = getTalentById(talentId);
        UserInfo userInfo = getUserInfo(authentication.getName());
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.valueOf(direction.toUpperCase()),
                sortProperties
        );

        Page<TalentProof> proofs = talent.equals(userInfo.getTalent())
                ? talentProofRepository.findByTalentId(talentId, pageRequest)
                : talentProofRepository.findByTalentIdAndStatus(talentId, ProofStatus.PUBLISHED, pageRequest);

        return FullProofDTO.builder()
                .id(talent.getId())
                .image(talent.getImage())
                .firstName(talent.getFirstName())
                .lastName(talent.getLastName())
                .specialization(talent.getSpecialization())
                .proofs(proofs.map(i -> ProofDTO.builder()
                        .id(i.getId())
                        .created(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(i.getCreated()))
                        .link(i.getLink())
                        .text(i.getText())
                        .status(i.getStatus())
                        .build()))
                .build();
    }

    public ResponseEntity<?> addProof(AddProof addProof, long talentId, Authentication authentication) {
        Talent talent = getTalentById(talentId);
        UserInfo userInfo = getUserInfo(authentication.getName());

        if (!talent.getId().equals(userInfo.getTalent().getId())) {
            throw new ResponseStatusException(FORBIDDEN);
        }

        TalentProof talentProof = TalentProof.builder()
                .talent(talent)
                .link(addProof.link())
                .text(addProof.text())
                .status(ProofStatus.DRAFT)
                .created(LocalDateTime.now())
                .build();
        talentProofRepository.save(talentProof);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/talents/proofs/{id}")
                .buildAndExpand(talentProof.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public TalentProof editTalentProof(long talentId, long proofId, ProofDTO proof, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        Optional<TalentProof> oldTalentProof = talentProofRepository.findById(proofId);

        validateTalentForCompliance.userAndProofVerification(talent, oldTalentProof, userInfo, talentId, proofId);

        TalentProof talentProof = oldTalentProof.get();
        ProofStatus oldProofStatus = talentProof.getStatus();

        if (oldProofStatus != ProofStatus.DRAFT && proof.status() == ProofStatus.DRAFT)
            throw new ResponseStatusException(FORBIDDEN, "you cannot change proofs status to DRAFT");
        if (oldProofStatus == ProofStatus.DRAFT && proof.status() == ProofStatus.HIDDEN)
            throw new ResponseStatusException(FORBIDDEN, "you cannot change proofs status from DRAFT to HIDDEN, it should be PUBLISHED");

        if (proof.link() == null && proof.text() == null) {
            talentProof.setStatus(proof.status());
        } else {
            talentProof.setLink(proof.link() != null ? proof.link() : talentProof.getLink()).setText(proof.text() != null ? proof.text() : talentProof.getText()).setStatus(proof.status());
        }
        return talentProofRepository.save(talentProof);
    }

    public void deleteProofById(long talentId, long proofId, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<TalentProof> talentProof = talentProofRepository.findById(proofId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userAndProofVerification(talent, talentProof, userInfo, talentId, proofId);

        Talent updatableTalent = talent.get();

        List<Skill> allTalentSkills = updatableTalent.getTalentProofs().stream()
                .flatMap(proof -> proof.getProofSkills().stream().map(ProofSkill::getSkill))
                .collect(Collectors.toList());

        List<Skill> skillsOnProofForDelete = talentProof.get().getProofSkills()
                .stream().map(ProofSkill::getSkill).toList();

        allTalentSkills.removeAll(skillsOnProofForDelete);
        Set<Skill> newTalentSkills = new HashSet<>(allTalentSkills);
        updatableTalent.setSkills(newTalentSkills);
        log.info("new talent-skills = {}", newTalentSkills.stream().map(Skill::getSkill).toList());
        updatableTalent.getTalentProofs().remove(talentProof.get());
    }

    private TalentProof getTalentProof(long proofId) {
        return talentProofRepository.findById(proofId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "proof with id = %d not found".formatted(proofId)));
    }

    private UserInfo getUserInfo(String login) {
        return userInfoRepository.findByLogin(login).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "user with login = {%s} not found".formatted(login)));
    }

    private Talent getTalentById(long talentId) {
        return talentRepository.findById(talentId).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "talent with id = {%s} not found".formatted(talentId)));
    }
}