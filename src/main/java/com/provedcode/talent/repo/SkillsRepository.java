package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    @Query("select s from Skills s where upper(s.skill) like upper(concat('%', ?1, '%'))")
    List<Skills> findBySkillContainsIgnoreCase(String skill);
}