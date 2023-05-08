package com.provedcode.kudos.model.entity;

import com.provedcode.sponsor.model.entity.Sponsor;
import com.provedcode.talent.model.entity.TalentProof;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kudos")
public class Kudos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "amount")
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proof_id")
    private TalentProof proof;
}