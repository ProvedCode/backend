package com.provedcode.user.model.entity;

import com.provedcode.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "login", length = 100)
    private String login;
    @Column(name = "password")
    private String password;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "id")
    private Talent talent;
    @OneToMany(mappedBy = "userInfo", orphanRemoval = true)
    private Set<UserAuthority> userAuthorities = new LinkedHashSet<>();
}