package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TalentRepository {

    List<Talent> getTalentsPage(PageRequest page);

    Optional<Talent> findById(Long aLong);
}