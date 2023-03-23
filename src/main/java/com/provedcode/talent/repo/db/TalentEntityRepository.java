package com.provedcode.talent.repo.db;

import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.repo.TalentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TalentEntityRepository extends
        JpaRepository<Talent, Long>,
        TalentRepository {
    @Override
    default List<Talent> findTalentsPage(PageRequest page) {
        return findAll(page).stream().toList();
    }

    @Override
    Optional<Talent> findById(Long aLong);
}