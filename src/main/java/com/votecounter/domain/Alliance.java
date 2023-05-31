package com.votecounter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This part cant be empty..")
    @Column(unique = true)
    private String AllianceName;

    @OneToMany
    @JoinColumn(name = "alliance_Id", referencedColumnName = "id")
    private Set<Party>partyList;
}
