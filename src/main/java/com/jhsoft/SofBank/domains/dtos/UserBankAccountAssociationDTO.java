package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.services.TypeRol;
import lombok.*;

@Getter
@Setter
public class UserBankAccountAssociationDTO {

//    private BankAccount bankAccount;
//    private Users users;
//    private TypeRol role;
    private String bankAccount;
    private String users;
    private TypeRol role;
}
