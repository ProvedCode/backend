package com.provedcode.talent.service;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.FullProofDTO;
import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
@Slf4j
public class TalentProofService {
    TalentProofRepository talentProofRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;

    public Page<TalentProof> getAllProofsPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        return talentProofRepository.findByStatus(ProofStatus.PUBLISHED,
                PageRequest.of(page.orElse(
                                pageProperties.defaultPageNum()),
                        size.orElse(
                                pageProperties.defaultPageSize())));
    }

    public FullProofDTO getTalentProofs(Long talentId, Optional<Integer> page, Optional<Integer> size,
                                        Optional<String> direction, Authentication authentication, String... sortProperties) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Talent with id = %s not found".formatted(talentId)));
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Talent with id = %s not found".formatted(talentId)));
        Page<TalentProof> proofs;
        PageRequest pageRequest;
        String sortDirection = direction.orElseGet(Sort.DEFAULT_DIRECTION::name);

        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        if (!sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) && !sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name())) {
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
}
