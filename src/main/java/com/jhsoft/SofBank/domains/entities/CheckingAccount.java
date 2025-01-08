package com.jhsoft.SofBank.domains.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idBankAccount")
public class CheckingAccount extends BankAccount{

    @Column(nullable = false)
    private double rateInterest;

//    @JoinColumn(name = "idBankAccount", referencedColumnName = "idBankAccount", insertable = false, updatable = false)
//    private BankAccount bankAccount;

    public CheckingAccount(String numberAccount, double balance, double rateInterest){
        super(numberAccount, balance,TypeAccount.CORRIENTE);
        this.rateInterest = rateInterest;
    }

    public void setRateInterest(double rateInterest){
        if(rateInterest <= 0){
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa.");
        }
        this.rateInterest= rateInterest;
    }
}
