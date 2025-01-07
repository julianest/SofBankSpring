package com.jhsoft.SofBank.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "BankAccount")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBankAccount;

    @Column(nullable = false, unique = true, length = 20)
    private String numberAccount;

    @Column(nullable = false, length = 10)
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double interestAccumulated;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false, length = 20)
    private String createdBy;

    /*@CreationTimestamp
    @Column(nullable = false)
    private Timestamp modifyAt;

    @Column(length = 20)
    private String modifyBy;*/

    @OneToMany(mappedBy = "bankAccount", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<UserBankAccountAssociation> userAssociations = new ArrayList<>();

    public BankAccount(String numberAccount, double initialbalance, TypeAccount typeAccount ){
        this.numberAccount = numberAccount;
        this.balance = initialbalance;
        this.typeAccount = typeAccount;
    }

}
