package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "talent_skill")
public class TalentSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "talent_id", nullable = false)
    private Long talentId;
    @Column(name = "skill")
    private String skill;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}