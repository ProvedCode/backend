package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TalentRepository {

    Page<Talent> findAll(Pageable pageable);

    Optional<Talent> findById(Long aLong);
}