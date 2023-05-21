package com.provedcode.user.repo;

import com.provedcode.user.model.entity.DeletedUser;
import com.provedcode.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
    Optional<DeletedUser> findByUuidForActivate(String uuidForActivate);
    long deleteByDeletedUser(UserInfo deletedUser);

}