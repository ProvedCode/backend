package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
}