package com.provedcode.user.model.entity;

import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.talent.model.entity.Talent;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Builder
@Entity
public class DeletedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant timeToDelete;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserInfo deletedUser;
}