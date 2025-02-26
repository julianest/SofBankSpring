package com.jhsoft.sofbank.domains.entities;

import com.jhsoft.sofbank.utils.enums.TypeRol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserLogin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeRol typeRol;
    /*
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();*/
}
