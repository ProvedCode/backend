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
@Table(name = "talent_talents")
public class TalentTalents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "talent_id", nullable = false)
    private Long talentId;
    @Column(name = "talent_name")
    private String talentName;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}