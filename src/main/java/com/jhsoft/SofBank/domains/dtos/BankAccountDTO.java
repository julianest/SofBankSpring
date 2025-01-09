package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.domains.entities.TypeAccount;
import lombok.*;

@Getter
@Setter
public class BankAccountDTO {

    private String numberAccount;
    private double balance;
    private double rateInterest;
    private double interestAccumulated;
    private TypeAccount typeAccount;

    public BankAccountDTO(String number, double v, double v1, TypeAccount typeAccount) {
    }
}
