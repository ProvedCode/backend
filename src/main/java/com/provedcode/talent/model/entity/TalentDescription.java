package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
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
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "talent_id", updatable = false)
    private Talent talent;
    @Column(name = "BIO")
    private String bio;
    @Column(name = "addition_info")
    private String additionalInfo;
}