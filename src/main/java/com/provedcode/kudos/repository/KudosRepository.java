package com.provedcode.kudos.repository;

import com.provedcode.kudos.model.entity.Kudos;
import com.provedcode.talent.model.entity.Talent;
import com.provedcode.talent.model.entity.TalentProof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudosRepository extends JpaRepository<Kudos, Long> {
    long countByProof_Id(Long id);

    boolean existsByTalent(Talent talent);
}