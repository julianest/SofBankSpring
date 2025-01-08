package com.jhsoft.SofBank.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    public Users(String identification, String name, String lastName, String email) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsers;

    @Column(nullable = false, length = 50)
    private String identification;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false, length = 20)
    private String createdBy;
/*
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp modifyAt;

    @Column(nullable = false, length = 20)
    private String modifyBy;*/

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private List<UserBankAccountAssociation> bankAssociations = new ArrayList<>();

}
