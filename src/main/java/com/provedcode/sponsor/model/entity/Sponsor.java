package com.provedcode.sponsor.model.entity;

import com.provedcode.kudos.model.entity.Kudos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "amount_kudos")
    private Long amountKudos;
    @NotEmpty
    @Column(name = "first_name", length = 20)
    private String firstName;
    @NotEmpty
    @Column(name = "last_name", length = 20)
    private String lastName;
    @URL
    @Column(name = "image", length = 300)
    private String image;
    @OneToMany(mappedBy = "sponsor")
    private List<Kudos> kudoses = new ArrayList<>();
}