package com.provedcode.talent.service;

import com.provedcode.talent.model.entity.Skill;
import com.provedcode.talent.repo.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {
    private final SkillsRepository skillsRepository;

    public List<Skill> getFilteredSkills(String filterBy) {
        return skillsRepository.findBySkillsBySubstring(filterBy).stream().toList();
    }
}
