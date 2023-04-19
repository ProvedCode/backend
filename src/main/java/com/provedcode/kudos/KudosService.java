package com.provedcode.kudos;

import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.kudos.model.response.KudosAmount;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import com.provedcode.talent.repo.TalentProofRepository;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.user.model.entity.UserInfo;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class KudosService {
    KudosRepository kudosRepository;
    TalentRepository talentRepository;
    TalentProofRepository talentProofRepository;
    UserInfoRepository userInfoRepository;

    public KudosAmount getAmountKudosProof(long id) {
        long count = kudosRepository.countByProof_Id(id);
        return new KudosAmount(count);
    }

    public void addKudosToProof(long id, Authentication authentication) {
        UserInfo userInfo = userInfoRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Talent with id = %s not found".formatted(id)));

        Talent talent = userInfo.getTalent();

        TalentProof talentProof = talentProofRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Proof with id = %s not found".formatted(id)));

        if (talent.getId() == talentProof.getTalent().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Talent can’t give “kudos“ to himself");
        }

        if (kudosRepository.existsByTalent(talent)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Talent can give only one “kudos“ for one proof");
        }

        kudosRepository.save(Kudos.builder()
                .proof(talentProof)
                .talent(talent)
                .build());
    }
}