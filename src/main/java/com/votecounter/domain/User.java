package com.votecounter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 11, nullable = false, unique = true)
    private String citNumber;

    @Column(length = 120, nullable = false)
    private String password;


    @Column(nullable = false)
    private Boolean builtIn = false ; // silinmemesi gereken user için bunu yaptık. dafault olarak false yaptık. ancak ilk admin
    // oluşturulurken bu özelliği TRUe yaparız. Bunu da bussiness logic olarak yapacağız


    @ManyToMany // LAZY
    @JoinTable( name="t_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();









}
