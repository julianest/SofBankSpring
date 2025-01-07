package com.jhsoft.SofBank.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class UserBankAccountAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserBankAccountAssoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "idBankAccount", nullable = false)
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "idUsers", nullable = false)
    private Users users;

    @Column(nullable = true)
    private String role;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp associatedAt;

}
