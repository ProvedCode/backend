package com.provedcode.user.repo;

import com.provedcode.user.model.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    Optional<UserAuthority> findByAuthority_Authority(String authority);
}