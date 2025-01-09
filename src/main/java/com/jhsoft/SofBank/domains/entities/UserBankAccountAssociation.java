package com.jhsoft.SofBank.domains.entities;

import com.jhsoft.SofBank.domains.services.TypeRol;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserBankAccountAssociation {

    public UserBankAccountAssociation(BankAccount bankAccount, Users users, TypeRol role) {
        this.bankAccount = bankAccount;
        this.users = users;
        this.role = role;
    }

    public UserBankAccountAssociation() {
    }

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
    @Enumerated(EnumType.STRING)
    private TypeRol role;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime associatedAt;

    @Column(nullable = false, updatable = false, length = 20)
    private String associatedBy;

}
