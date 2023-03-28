package com.provedcode.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_authority")
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}