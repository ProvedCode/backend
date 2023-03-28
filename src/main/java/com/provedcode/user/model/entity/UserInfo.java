package com.provedcode.user.model.entity;

import com.provedcode.talent.model.entity.Talent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "user_id")
    private Long userId;
    @NotEmpty
    @NotNull
    @Column(name = "login", length = 100)
    private String login;
    @NotEmpty
    @NotNull
    @Column(name = "password")
    private String password;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Talent talent;
    @OneToMany(mappedBy = "userInfo", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserAuthority> userAuthorities = new LinkedHashSet<>();
}