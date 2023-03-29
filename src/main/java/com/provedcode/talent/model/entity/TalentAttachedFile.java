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
@Table(name = "talent_attached_file")
public class TalentAttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "talent_id", nullable = false)
    private Long talentId;
    @URL
    @Column(name = "attached_file", length = 100)
    private String attachedFile;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "talent_id", insertable = false, updatable = false)
    private Talent talent;
}