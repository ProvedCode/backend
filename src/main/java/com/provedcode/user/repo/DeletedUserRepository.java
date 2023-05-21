package com.provedcode.user.repo;

import com.provedcode.user.model.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}