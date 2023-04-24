package com.provedcode.kudos.service;

import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.kudos.model.response.KudosAmount;
import com.provedcode.kudos.model.response.KudosAmountWithSponsor;
import com.provedcode.kudos.repository.KudosRepository;
import com.provedcode.sponsor.mapper.SponsorMapper;
import com.provedcode.sponsor.model.dto.SponsorDTO;
import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.sponsor.repository.SponsorRepository;
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


    public void addKudosToProof(long id, Long amount, Authentication authentication) {
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Sponsor with id = %s not found".formatted(id)));
        Sponsor sponsor = sponsorRepository.findById(userInfo.getSponsor().getId()).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND,
                        String.format("sponsor with id = %d not found", id)));
        TalentProof talentProof = talentProofRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Proof with id = %s not found".formatted(id)));
        if (sponsor.getAmountKudos() < amount) {
            throw new ResponseStatusException(FORBIDDEN, "The sponsor cannot give more kudos than he has");
        }
        if (amount <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "amount of kudos must be greater than 0");
        }
        sponsor.setAmountKudos(sponsor.getAmountKudos() - amount);
        kudosRepository.save(Kudos.builder()
                .amountKudos(amount)
                .proof(talentProof)
                .sponsor(sponsor)
                .build());
    }

    @Transactional(readOnly = true)
    public KudosAmount getKudosForSponsor(long id, Authentication authentication) {
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Sponsor with id = %s not found".formatted(id)));
        Sponsor sponsor = sponsorRepository.findById(userInfo.getSponsor().getId()).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND,
                        String.format("Sponsor with id = %d not found", id)));
        if (sponsor.getId() != userInfo.getSponsor().getId()) {
            throw new ResponseStatusException(FORBIDDEN, "Only the account owner can view the number of kudos");
        }
        return new KudosAmount(sponsor.getAmountKudos());
    }

    @Transactional(readOnly = true)
    public KudosAmountWithSponsor getProofKudos(long id, Authentication authentication) {
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Sponsor with id = %s not found".formatted(id)));
        Talent talent = talentRepository.findById(userInfo.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Talent with id = %s not found".formatted(id)));
        TalentProof talentProof = talentProofRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Proof with id = %s not found".formatted(id)));

        if (talent.getId() == talentProof.getTalent().getId()) {
            Map<SponsorDTO, Long> kudosFromSponsor = talentProof.getKudos().stream()
                    .collect(Collectors.toMap(
                            sponsor -> sponsorMapper.toDto(sponsor.getSponsor()),
                            Kudos::getAmountKudos,
                            (sponsor, kudos) -> sponsor,
                            HashMap::new
                    ));
            Long counter = talentProof.getKudos().stream().map(i -> i.getAmountKudos())
                    .mapToLong(Long::intValue).sum();
            return KudosAmountWithSponsor.builder()
                    .allKudosOnProof(counter)
                    .kudosFromSponsor(kudosFromSponsor)
                    .build();
        } else {
            Long counter = talentProof.getKudos().stream().map(i -> i.getAmountKudos())
                    .mapToLong(Long::intValue).sum();
            return KudosAmountWithSponsor.builder()
                    .allKudosOnProof(counter)
                    .kudosFromSponsor(null).build();
        }
    }
}