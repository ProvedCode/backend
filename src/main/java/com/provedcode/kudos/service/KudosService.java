package com.provedcode.kudos.service;

import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.kudos.model.request.SetAmountKudos;
import com.provedcode.kudos.model.response.KudosAmount;
import com.provedcode.kudos.model.response.KudosAmountWithSponsor;
import com.provedcode.kudos.repository.KudosRepository;
import com.provedcode.sponsor.mapper.SponsorMapper;
import com.provedcode.sponsor.model.dto.SponsorDTO;
import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.sponsor.repository.SponsorRepository;
import com.provedcode.talent.model.ProofStatus;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Transactional
public class KudosService {
    KudosRepository kudosRepository;
    TalentProofRepository talentProofRepository;
    UserInfoRepository userInfoRepository;
    SponsorRepository sponsorRepository;
    TalentRepository talentRepository;
    SponsorMapper sponsorMapper;

    public void addKudosToProof(long proofId, Optional<SetAmountKudos> setAmountKudos, Authentication authentication) {
        String login = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "User with login = %s not found".formatted(
                                login)));
        Sponsor sponsor = sponsorRepository.findById(userInfo.getSponsor().getId()).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND,
                        "Sponsor with login = %s not found".formatted(login)));
        TalentProof talentProof = talentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Proof with id = %d not found".formatted(
                                proofId)));

        if (kudosRepository.existsBySponsorIdAndProofId(sponsor.getId(), talentProof.getId()))
            throw new ResponseStatusException(FORBIDDEN, "The sponsor has already set kudos to this proof");
        if (!talentProof.getStatus().equals(ProofStatus.PUBLISHED))
            throw new ResponseStatusException(FORBIDDEN, "Proof that was kudosed does not have the PUBLISHED status");

        long obtainedAmount = setAmountKudos.orElse(new SetAmountKudos(1L)).amount();
        if (sponsor.getAmountKudos() < obtainedAmount) {
            throw new ResponseStatusException(FORBIDDEN, "The sponsor cannot give more kudos than he has");
        }
        sponsor.setAmountKudos(sponsor.getAmountKudos() - obtainedAmount);
        kudosRepository.save(Kudos.builder()
                .amount(obtainedAmount)
                .proof(talentProof)
                .sponsor(sponsor)
                .build());
    }

    @Transactional(readOnly = true)
    public KudosAmount getKudosForSponsor(long sponsorId, Authentication authentication) {
        String login = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "User with login = %s not found".formatted(
                                login)));
        if (!userInfo.getSponsor().getId().equals(sponsorId)) {
            throw new ResponseStatusException(FORBIDDEN, "Only the account owner can view the number of kudos");
        }
        Sponsor sponsor = sponsorRepository.findById(sponsorId).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND,
                        String.format("Sponsor with id = %d not found", sponsorId)));
        return new KudosAmount(sponsor.getAmountKudos());
    }

    @Transactional(readOnly = true)
    public KudosAmountWithSponsor getProofKudos(long proofId, Authentication authentication) {
        String login = authentication.getName();
        UserInfo userInfo = userInfoRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "User with login = %s not found".formatted(
                                login)));
        Talent talent = talentRepository.findById(userInfo.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Talent with login = %s not found".formatted(
                                login)));
        TalentProof talentProof = talentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Proof with id = %s not found".formatted(
                                proofId)));

        Long countOfAllKudos = talentProof.getKudos().stream()
                .map(Kudos::getAmount)
                .reduce(0L, (prev, next) -> prev + next);

        if (talent.getId().equals(talentProof.getTalent().getId())) {
            Map<Long, SponsorDTO> kudosFromSponsor = talentProof.getKudos().stream()
                    .collect(Collectors.toMap(
                            Kudos::getAmount,
                            proof -> proof.getSponsor() != null
                                    ? sponsorMapper.toDto(
                                    proof.getSponsor())
                                    : SponsorDTO.builder().build(),
                            (prev, next) -> next,
                            HashMap::new
                    ));

            return KudosAmountWithSponsor.builder()
                    .allKudosOnProof(countOfAllKudos)
                    .kudosFromSponsor(kudosFromSponsor)
                    .build();
        } else {
            return KudosAmountWithSponsor.builder()
                    .allKudosOnProof(countOfAllKudos)
                    .kudosFromSponsor(null).build();
        }
    }
}