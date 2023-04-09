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
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.talent.utill.ValidateTalentForCompliance;
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
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class TalentProofService {
    TalentProofRepository talentProofRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    ValidateTalentForCompliance validateTalentForCompliance;

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

    @Transactional
    public SessionInfoDTO deleteProofById(long talentId, long proofId, Authentication authentication) {

        Optional<Talent> talent = talentRepository.findById(talentId);
        Optional<TalentProof> talentProof = talentProofRepository.findById(proofId);
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        validateTalentForCompliance.userVerification(talent, talentProof, userInfo, talentId, proofId);
        talentProofRepository.delete(talentProof.orElseThrow(() -> new ResponseStatusException(NOT_IMPLEMENTED)));
        return new SessionInfoDTO("deleted", "null");
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
                .path("/{id}")
                .buildAndExpand(talentProof.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

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
                        .created(i.getCreated().toString())
                        .link(i.getLink())
                        .text(i.getText())
                        .status(i.getStatus()).build()))
                .build();
    }

    public ProofDTO getTalentProof(long talentId, long proofId, Authentication authentication) {
        Optional<TalentProof> talentProof = talentProofRepository.findById(proofId);
        if (talentProof.isPresent()) {
            if (talentProof.get().getTalentId() != talentId) {
                throw new ResponseStatusException(BAD_REQUEST,
                        String.format("proof with id = %d not equal to talent id = %d", proofId, talentId));
            }
        } else {
            throw new ResponseStatusException(NOT_FOUND, String.format("proof with id = %d not found", proofId));
        }
        Optional<UserInfo> userInfo = userInfoRepository.findByLogin(authentication.getName());
        if (userInfo.get().getTalentId() == talentId ||
                talentProof.get().getStatus().equals(ProofStatus.PUBLISHED)) {
            return ProofDTO.builder()
                    .id(talentProof.get().getTalentId())
                    .link(talentProof.get().getLink())
                    .status(talentProof.get().getStatus())
                    .created(talentProof.get().getCreated().toString())
                    .text(talentProof.get().getText())
                    .build();
        } else {
            throw new ResponseStatusException(FORBIDDEN);
        }
    }
}