package com.provedcode.talent;

import com.provedcode.service.TalentService;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentSkill;
import com.provedcode.talent.model.response.ShortTalent;
import com.provedcode.talent.repo.TalentRepository;
import com.provedcode.talent.repo.db.TalentEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TalentController {
    TalentEntityRepository talentRepository;
    TalentService talentService;

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

    @GetMapping("/api/talents")
    @ResponseStatus(HttpStatus.OK)
    List<ShortTalentDTO> getTalents(@RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size) {
        return talentService.getTalentsPage(page, size);
    }
}
