package com.provedcode.talent;

import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.service.TalentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api")
public class TalentController {
    TalentService talentService;

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/talents/{id}")
    FullTalentDTO getTalent(@PathVariable("id") long id) {
        return talentService.getTalentById(id);
    }

    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    Page<ShortTalentDTO> getTalents(@RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size) {
        return talentService.getTalentsPage(page, size);
    }

}
