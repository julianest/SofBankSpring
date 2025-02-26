package com.jhsoft.sofbank.domains.entities;

import com.jhsoft.sofbank.utils.enums.TypeAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idBankAccount")
public class SavingsAccount extends BankAccount{

    @Column(nullable = false)
    private double rateInterest;

    public SavingsAccount(String numberAccount, double balance, double rateInterest) {
        super(numberAccount, balance, TypeAccount.AHORRO);
        this.rateInterest = rateInterest;
    }

    public void setRateInterest(double rateInterest){
        if(rateInterest <= 0){
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa.");
        }
        this.rateInterest= rateInterest;
    }
}
