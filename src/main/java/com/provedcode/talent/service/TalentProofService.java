package com.provedcode.talent.service;

import com.provedcode.config.PageProperties;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.dto.AddProofDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.dto.SessionInfoDTO;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import com.provedcode.utill.ValidateTalentForCompliance;
import lombok.AllArgsConstructor;
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
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class TalentProofService {
    TalentProofRepository talentProofRepository;
    TalentRepository talentRepository;
    UserInfoRepository userInfoRepository;
    PageProperties pageProperties;
    ValidateTalentForCompliance validateTalentForCompliance;

    public Page<TalentProof> getAllProofsPage(Optional<Integer> page, Optional<Integer> size,
                                              Optional<String> sortDir) {
        if (page.orElse(pageProperties.defaultPageNum()) < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'page' query parameter must be greater than or equal to 0");
        }
        if (size.orElse(pageProperties.defaultPageSize()) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "'size' query parameter must be greater than or equal to 1");
        }

        if (sortDir.isPresent()) {
            if (!sortDir.get().equalsIgnoreCase(Sort.Direction.ASC.name()) &&
                    !sortDir.get().equalsIgnoreCase(Sort.Direction.DESC.name())) {
                throw new ResponseStatusException(BAD_REQUEST, "'sortDir' query parameter must be ASC or DESC");
            }
            Sort sort = sortDir.get().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(pageProperties.defaultSortBy()).ascending()
                    : Sort.by(pageProperties.defaultSortBy()).descending();
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
}
