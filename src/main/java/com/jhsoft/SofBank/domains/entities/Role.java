/*package com.jhsoft.SofBank.domains.entities;

import com.jhsoft.SofBank.domains.services.TypeRol;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TypeRol typeRol;

    @Column(length = 100)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<UserLogin> users = new HashSet<>();

    public Role(Object o, TypeRol typeRol, String administradorDelSistema) {
    }
}*/
