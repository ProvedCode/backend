package com.provedcode.kudos.repository;

import com.provedcode.kudos.model.entity.Kudos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KudosRepository extends JpaRepository<Kudos, Long> {
    long countByProofId(Long id);

    boolean existsBySponsorIdAndProofId(Long sponsorId, Long proofId);
}