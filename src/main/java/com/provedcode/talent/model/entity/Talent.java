package com.provedcode.talent.model.entity;

import com.provedcode.kudos.model.entity.Kudos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentLink> talentLinks = new ArrayList<>();
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentTalents> talentTalents = new ArrayList<>();
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentContact> talentContacts = new ArrayList<>();
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentAttachedFile> talentAttachedFiles = new ArrayList<>();
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TalentProof> talentProofs = new ArrayList<>();
    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kudos> cudoses = new ArrayList<>();
}