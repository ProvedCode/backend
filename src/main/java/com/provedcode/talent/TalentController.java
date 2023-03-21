package com.provedcode.talent;

import com.provedcode.service.TalentService;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.Talent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TalentController {
    TalentService talentService;

    @GetMapping("/api/talents/{id}")
    Talent getTalent(@PathVariable("id") long id) {
        return talentService.getTalentById(id);
    }

    @GetMapping("/api/talents")
    @ResponseStatus(HttpStatus.OK)
    List<ShortTalentDTO> getTalents(@RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size) {
        return talentService.getTalentsPage(page, size);
    }
}
