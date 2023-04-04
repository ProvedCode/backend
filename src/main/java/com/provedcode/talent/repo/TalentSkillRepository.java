package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentTalents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentSkillRepository extends JpaRepository<TalentTalents, Long> {
    List<TalentTalents> deleteByTalent(Talent talent);
}