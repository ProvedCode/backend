package com.provedcode.talent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Builder
@Accessors(chain = true)
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
    @Column(name = "first_name", length = 20)
    private String firstName;
    @NotEmpty
    @Column(name = "last_name", length = 20)
    private String lastName;
    @NotEmpty
    @Column(name = "specialization", length = 30)
    private String specialization;
    @URL
    @Column(name = "image", length = 100)
    private String image;
    @OneToOne(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private TalentDescription talentDescription;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentLink> talentLinks = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentSkill> talentSkills = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentContact> talentContacts = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentAttachedFile> talentAttachedFiles = new ArrayList<>();

    public void addTalentSkill(TalentSkill talentSkill) {
        talentSkills.add(talentSkill);
    }

    public void removeTalentSkill(TalentSkill talentSkill) {
        talentSkills.remove(talentSkill);
    }
}