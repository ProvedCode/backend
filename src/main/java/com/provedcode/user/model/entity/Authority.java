package com.provedcode.user.model.entity;

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
    @NotEmpty
    @NotNull
    @Column(name = "authority", length = 20)
    private String authority;
    @ManyToMany(mappedBy = "authorities", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserInfo> userInfoes = new LinkedHashSet<>();
}