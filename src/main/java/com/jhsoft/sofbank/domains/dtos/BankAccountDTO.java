package com.jhsoft.sofbank.domains.dtos;

import com.jhsoft.sofbank.utils.enums.TypeAccount;
import lombok.*;

@Getter
@Setter
public class BankAccountDTO {

    private String numberAccount;
    private double balance;
    private double rateInterest;
    private double interestAccumulated;
    private TypeAccount typeAccount;

    public BankAccountDTO(String numberAccount, double balance, double rateInterest, double interestAccumulated, TypeAccount typeAccount) {
        this.numberAccount = numberAccount;
        this.balance = balance;
        this.rateInterest = rateInterest;
        this.interestAccumulated = interestAccumulated;
        this.typeAccount = typeAccount;
    }
}
