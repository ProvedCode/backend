package com.provedcode.talent.repo.db;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TalentEntityRepository extends
        JpaRepository<Talent, Long>,
        TalentRepository {
    @Transactional(readOnly = true)
    Page<Talent> findAll(Pageable pageable);
    @Override
    @Transactional(readOnly = true)
    Optional<Talent> findById(Long aLong);
}