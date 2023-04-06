package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "talent_proofs")
public class TalentProof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "talent_id", nullable = false)
    private Long talentId;
    @NotEmpty
    @URL
    @Column(name = "link", length = 100)
    private String link;
    private String text;
    private String status;
    private LocalDateTime created;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}