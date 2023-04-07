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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

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

    public FullProofDTO getTalentProofs(Long talentId, Optional<Integer> page, Optional<Integer> size, Authentication authentication) {
        Talent talent = talentRepository.findById(talentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Talent with id = %s not found".formatted(talentId)));
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Talent with id = %s not found".formatted(talentId)));
        Page<TalentProof> proofs = null;

        log.info("auth = {}", authentication);
        if (userInfo.getTalent().getId() != talentId) {
            proofs = talentProofRepository.findByTalentIdAndStatus(talentId, ProofStatus.PUBLISHED,
                    PageRequest.of(page.orElse(pageProperties.defaultPageNum()), size.orElse(pageProperties.defaultPageSize())));
        } else {
            proofs = talentProofRepository.findByTalentId(talentId,
                    PageRequest.of(page.orElse(pageProperties.defaultPageNum()), size.orElse(pageProperties.defaultPageSize())));
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
