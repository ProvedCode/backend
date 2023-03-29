package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentSkillRepository extends JpaRepository<TalentSkill, Long> {
    List<TalentSkill> deleteByTalent(Talent talent);
}