package com.provedcode.talent.model.entity;

import com.provedcode.talent.model.ProofStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Accessors(chain = true)
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
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProofStatus status;
    private LocalDateTime created;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}