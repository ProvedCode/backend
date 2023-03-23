package com.provedcode.talent.repo;

import com.provedcode.talent.model.entity.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface TalentRepository {

    List<Talent> findTalentsPage(PageRequest page);

    Optional<Talent> findById(Long aLong);
}