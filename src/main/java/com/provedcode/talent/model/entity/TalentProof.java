package com.provedcode.talent.model.entity;

import com.provedcode.cudos.model.Cudos;
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
    @ManyToOne
    @JoinColumn(name = "talent_id", updatable = false)
    private Talent talent;
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
    @OneToOne(mappedBy = "proof", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cudos cudos;
}