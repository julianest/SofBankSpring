package com.jhsoft.SofBank.domains.entities;

import com.jhsoft.SofBank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import com.jhsoft.SofBank.domains.strategy.SavingsAccountStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
