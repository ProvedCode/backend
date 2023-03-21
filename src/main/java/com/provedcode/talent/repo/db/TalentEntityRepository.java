package com.provedcode.talent.repo.db;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TalentEntityRepository extends
        JpaRepository<Talent, Long>,
        TalentRepository {
    @Transactional(readOnly = true)
    default List<Talent> getTalents() {
        return findAll();
    }

    @Override
    @Transactional(readOnly = true)
    default List<Talent> getTalentsPage(PageRequest page) {
        return findAll(page).stream().toList();
    }

    @Override
    Optional<Talent> findById(Long aLong);
}