package com.provedcode.talent.service;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.AddProofDTO;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class TalentProofService {
    TalentProofRepository talentProofRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    ValidateTalentForCompliance validateTalentForCompliance;

    @Transactional(readOnly = true)
    public Page<TalentProof> getAllProofsPage(Optional<Integer> page, Optional<Integer> size,
                                              Optional<String> orderBy) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }

        if (orderBy.isPresent()) {
            if (!orderBy.get().equalsIgnoreCase(Sort.Direction.ASC.name()) &&
                !orderBy.get().equalsIgnoreCase(Sort.Direction.DESC.name())) {
                throw new ResponseStatusException(BAD_REQUEST, "'orderBy' query parameter must be ASC or DESC");
            }
            Sort sort =
                    orderBy.get().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(pageProperties.defaultSortBy())
                                                                                    .ascending()
                                                                              : Sort.by(pageProperties.defaultSortBy())
                                                                                    .descending();
            return talentProofRepository.findByStatus(ProofStatus.PUBLISHED,
                                                      PageRequest.of(page.orElse(
                                                                             pageProperties.defaultPageNum()),
                                                                     size.orElse(
                                                                             pageProperties.defaultPageSize()), sort));
        }
        return talentProofRepository.findByStatus(ProofStatus.PUBLISHED,
                                                  PageRequest.of(page.orElse(
                                                                         pageProperties.defaultPageNum()),
                                                                 size.orElse(
                                                                         pageProperties.defaultPageSize())));
    }

    public TalentProof getTalentProof(long proofId, Authentication authentication) {
        TalentProof talentProof = talentProofRepository.findById(proofId).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, String.format("proof with id = %d not found", proofId)));
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName()).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "user with this token not found"));

        if (talentProof.getTalentId().equals(userInfo.getTalentId()) ||
            talentProof.getStatus().equals(ProofStatus.PUBLISHED)) {
            return talentProof;
        } else {
            throw new ResponseStatusException(FORBIDDEN);
        }
    }

    @Transactional(readOnly = true)
    public FullProofDTO getTalentProofs(Long talentId, Optional<Integer> page, Optional<Integer> size,
                                        Optional<String> direction, Authentication authentication,
                                        String... sortProperties) {
        Talent talent = talentRepository.findById(talentId)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                       "Talent with id = %s not found".formatted(
                                                                                               talentId)));
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                             "Talent with id = %s not found".formatted(
                                                                                                     talentId)));
        Page<TalentProof> proofs;
        PageRequest pageRequest;
        String sortDirection = direction.orElseGet(Sort.DEFAULT_DIRECTION::name);

        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        if (!sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) &&
            !sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            throw new ResponseStatusException(BAD_REQUEST, "'direction' query param must be equals ASC or DESC");
        }

        try {
            pageRequest = PageRequest.of(
                    page.orElse(pageProperties.defaultPageNum()),
                    size.orElse(pageProperties.defaultPageSize()),
                    Sort.Direction.valueOf(sortDirection.toUpperCase()),
                    sortProperties
            );
            if (!userInfo.getLogin().equals(authentication.getName())) {
                proofs = talentProofRepository.findByTalentIdAndStatus(talentId, ProofStatus.PUBLISHED, pageRequest);
            } else {
                proofs = talentProofRepository.findByTalentId(talentId, pageRequest);
            }
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(BAD_REQUEST, exception.getMessage());
        }

        return FullProofDTO.builder()
                           .id(talent.getId())
                           .image(talent.getImage())
                           .firstName(talent.getFirstName())
                           .lastName(talent.getLastName())
                           .specialization(talent.getSpecialization())
                           .proofs(proofs.map(i -> ProofDTO.builder()
                                                           .id(i.getId())
                                                           .created(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                                                                                     .format(i.getCreated()))
                                                           .link(i.getLink())
                                                           .text(i.getText())
                                                           .status(i.getStatus()).build()))
                           .build();
    }

    public ResponseEntity<?> addProof(AddProofDTO addProofDTO, long talentId, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());

        validateTalentForCompliance.userVerification(talent, userInfo, talentId);

        TalentProof talentProof = TalentProof.builder()
                                             .talent(talent.get())
                                             .talentId(talentId)
                                             .link(addProofDTO.link())
                                             .text(addProofDTO.text())
                                             .status(ProofStatus.DRAFT)
                                             .created(LocalDateTime.now())
                                             .build();

        talentProofRepository.save(talentProof);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/api/talents/proofs/{id}")
                .buildAndExpand(talentProof.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public TalentProof editTalentProof(long talentId, long proofId, ProofDTO proof, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        Optional<TalentProof> talentProof = talentProofRepository.findById(proofId);

        validateTalentForCompliance.userAndProofVerification(talent, talentProof, userInfo, talentId, proofId);

        TalentProof oldProof = talentProof.get();
        ProofStatus oldProofStatus = oldProof.getStatus();

        if (oldProofStatus != ProofStatus.DRAFT && proof.status() == ProofStatus.DRAFT)
            throw new ResponseStatusException(FORBIDDEN, "you cannot change proofs status to DRAFT");
        if (oldProofStatus == ProofStatus.DRAFT && proof.status() == ProofStatus.HIDDEN)
            throw new ResponseStatusException(FORBIDDEN,
                                              "you cannot change proofs status from DRAFT to HIDDEN, it should be PUBLISHED");

        if (proof.link() == null && proof.text() == null) {
            oldProof.setStatus(proof.status());
        } else {
            if (oldProofStatus != ProofStatus.DRAFT)
                throw new ResponseStatusException(FORBIDDEN, "you cannot edit proofs without DRAFT status");

            oldProof.setLink(proof.link())
                    .setText(proof.text() != null ? proof.text() : oldProof.getText())
                    .setStatus(proof.status());
        }
        return talentProofRepository.save(oldProof);
    }

    public SessionInfoDTO deleteProofById(long talentId, long proofId, Authentication authentication) {
        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<TalentProof> talentProof = talentProofRepository.findById(proofId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        validateTalentForCompliance.userAndProofVerification(talent, talentProof, userInfo, talentId, proofId);
        talentProofRepository.delete(talentProof.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED)));
        return new SessionInfoDTO("deleted", "null");
    }
}