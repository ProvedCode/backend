package com.provedcode.user.mapper;

import com.provedcode.user.model.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfoMapper {
    UserDetails toUserDetails(UserInfo user);
}
