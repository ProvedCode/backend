package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "talent_description")
public class TalentDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotNull
    @Column(name = "talent_id", nullable = false)
    private Long talentId;
    @Column(name = "BIO")
    private String bio;
    @Column(name = "addition_info")
    private String additionalInfo;
    @NotNull
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}