package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentTalents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TalentRepository extends JpaRepository<Talent, Long> {
}