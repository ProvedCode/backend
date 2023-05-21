package com.provedcode.user.repo;

import com.provedcode.user.model.entity.DeletedUser;
import com.provedcode.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
    long deleteByDeletedUser(UserInfo deletedUser);

}