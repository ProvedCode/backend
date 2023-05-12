package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TalentRepository extends JpaRepository<Talent, Long> {
    @Query("select t from Talent t left join t.skills skills where upper(skills.skill) like upper(concat('%', ?1, '%'))")
    Page<Talent> findBySkills_SkillContainsIgnoreCase(String skill, Pageable pageable);
    @Query("select t from Talent t inner join t.skills skills where upper(skills.skill) like upper(?1)")
    Page<Talent> findBySkillsLikeIgnoreCase(String skill, Pageable pageable);

}