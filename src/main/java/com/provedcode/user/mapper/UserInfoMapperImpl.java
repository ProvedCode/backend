package com.provedcode.user.mapper;

import com.provedcode.user.model.entity.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapperImpl implements UserInfoMapper {
    @Override
    public UserDetails toUserDetails(UserInfo user) {
        return User.withUsername(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getUserAuthorities()
                        .stream()
                        .map(i -> new SimpleGrantedAuthority(
                                i.getAuthority()
                                .getAuthority()))
                        .toList())
                .build();
    }
}
