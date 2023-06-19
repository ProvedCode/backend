package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.ProofDTO;
import com.provedcode.talent.model.dto.SkillDTO;
import com.provedcode.talent.model.dto.SkillIdDTO;
import com.provedcode.talent.model.dto.StatisticsDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.request.EditTalent;
import com.provedcode.user.model.dto.SessionInfoDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public interface TalentService {
    @Transactional(readOnly = true)
    Page<Talent> getTalentsPage(Integer page, Integer size);

    @Transactional(readOnly = true)
    Talent getTalentById(long id);

    Talent editTalent(long id, EditTalent editTalent, Authentication authentication);

    SessionInfoDTO deleteTalentById(long id, Authentication authentication);

    void deactivateTalentById(long id, Authentication authentication);

    default void checkEditTalentNull(EditTalent editTalent) {
        if (editTalent.firstName() == null && editTalent.lastName() == null && editTalent.image() == null &&
                editTalent.specialization() == null && editTalent.additionalInfo() == null && editTalent.bio() == null &&
                editTalent.links() == null && editTalent.contacts() == null && editTalent.attachedFiles() == null)
            throw new ResponseStatusException(BAD_REQUEST, "you did not provide information to make changes");
    }

    void addSkillOnTalent(long id, SkillIdDTO skillIdDTO, Authentication authentication);

    void deleteSkillFromTalent(long talentId, long skillId, Authentication authentication);

    @Transactional(readOnly = true)
    Page<Talent> getFilteredBySkillsTalentsPage(@PositiveOrZero Integer page,
                                                @Min(1) @Max(1000) Integer size,
                                                String... filterBy);

    StatisticsDTO getStatisticsForTalent(long talentId, Authentication authentication);

    Long getAllKudosOnTalent(Talent talent);

    Map<String, Long> getSkillWithLargestNumberOfKudos(Talent talent);

    Map<ProofDTO, Long> getProofWithLargestNumberOfKudos(Talent talent);

    List<SkillDTO> getAllSkillsOnTalentsProofs(long talentId, Authentication authentication);
}
