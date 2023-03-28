package com.provedcode.user.repo;

import com.provedcode.user.model.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}