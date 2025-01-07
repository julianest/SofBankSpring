package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.domains.entities.TypeAccount;

public class BankAccountDTO {

    private String numberAccount;
    private double balance;
    private double rateInterest;
    private double interestAccumulated;
    private TypeAccount typeAccount;

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(TypeAccount typeAccount) {
        this.typeAccount = typeAccount;
    }

    public double getInterestAccumulated() {
        return interestAccumulated;
    }

    public void setInterestAccumulated(double interestAccumulated) {
        this.interestAccumulated = interestAccumulated;
    }

    public double getRateInterest() {
        return rateInterest;
    }

    public void setRateInterest(double rateInterest) {
        this.rateInterest = rateInterest;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }
}
