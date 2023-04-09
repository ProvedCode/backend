package com.provedcode.user.model.entity;

import com.provedcode.user.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotEmpty
    @NotNull
    @Column(name = "authority", length = 20)
    private Role authority;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authorities")
    private Set<UserInfo> userInfoes = new LinkedHashSet<>();
}