package com.jhsoft.sofbank.domains.dtos;

import com.jhsoft.sofbank.utils.enums.TypeAccount;
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
