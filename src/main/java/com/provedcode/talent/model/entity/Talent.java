package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "talent")
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    private Long id;
    @NotEmpty
    @NotNull
    @Column(name = "first_name", length = 20)
    private String firstName;
    @NotEmpty
    @NotNull
    @Column(name = "last_name", length = 20)
    private String lastName;
    @NotEmpty
    @NotNull
    @Column(name = "specialization", length = 30)
    private String specialization;
    @NotEmpty
    @NotNull
    @Column(name = "short_description")
    private String shortDescription;
    @URL
    @NotEmpty
    @NotNull
        @Column(name = "image", length = 100)
    private String image;
    @NotNull
    @OneToOne(mappedBy = "talent", orphanRemoval = true)
    private TalentDescription talentDescription;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", orphanRemoval = true)
    private List<TalentLink> talentLinks = new ArrayList<>();
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", orphanRemoval = true)
    private List<TalentSkill> talentSkills = new ArrayList<>();
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", orphanRemoval = true)
    private List<TalentContact> talentContacts = new ArrayList<>();
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", orphanRemoval = true)
    private List<TalentAttachedFile> talentAttachedFiles = new ArrayList<>();
}