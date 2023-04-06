//package com.provedcode.user.mapper.impl;
//
//import com.provedcode.user.mapper.UserInfoMapper;
//import com.provedcode.user.model.entity.UserInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class UserInfoMapperImpl implements UserInfoMapper {
//    @Override
//    public UserDetails toUserDetails(UserInfo user) {
//        return User.withUsername(user.getLogin())
//                   .password(user.getPassword())
//                   .authorities(user.getAuthorities()
//                                    .stream()
//                                    .map(i -> new SimpleGrantedAuthority(i.getAuthority()))
//                                    .toList())
//                   .build();
//    }
//}
