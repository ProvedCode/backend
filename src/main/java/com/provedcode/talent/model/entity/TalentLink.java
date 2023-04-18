package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "talent_link")
public class TalentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", updatable = false)
    private Talent talent;
    @URL
    @Column(name = "link")
    private String link;
}