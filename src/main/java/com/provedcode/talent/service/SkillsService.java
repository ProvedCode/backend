package com.provedcode.talent.service;

import com.provedcode.talent.model.dto.SkillDTO;

import java.util.List;

/**
 * Interface for skills service
 */
public interface SkillsService {

    /**
     *
     * @param filterBy
     * Inline string that must be in skill`s name
     * @return List of SkillDTO that can be empty
     */
    List<SkillDTO> getFilteredSkills(String filterBy);
}
