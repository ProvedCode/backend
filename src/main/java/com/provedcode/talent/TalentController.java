package com.provedcode.talent;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentSkill;
import com.provedcode.talent.model.response.ShortTalent;
import com.provedcode.talent.repo.TalentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TalentController {
    TalentRepository talentRepository;

    @GetMapping("/api/talent/{id}")
    ShortTalent getTalent(@PathVariable("id") long id) {
        Talent talent = talentRepository.findById(id)
                                        .orElseThrow(
                                                () -> new UsernameNotFoundException(
                                                        "id " + id + " not found"));
        return new ShortTalent(
                talent.getId(),
                talent.getImage(),
                talent.getFirstName(),
                talent.getLastName(),
                talent.getSpecialization(),
                talent.getTalentSkills().stream().map(TalentSkill::getSkill).collect(Collectors.toList())
        );
    }
}
