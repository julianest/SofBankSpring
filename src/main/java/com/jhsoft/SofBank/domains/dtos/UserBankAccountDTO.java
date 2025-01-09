package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.domains.entities.TypeAccount;
import lombok.*;

@Getter
@Setter
public class UserBankAccountDTO {
    private String identification;
    private String name;
    private String lastName;
    private String email;
    private String accountNumber;
    private double balance;
    private TypeAccount typeAccount;

    // Constructor
    public UserBankAccountDTO(String identification, String name, String lastName, String email,
                                  String accountNumber, double balance, TypeAccount typeAccount) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.typeAccount = typeAccount;
    }
}
