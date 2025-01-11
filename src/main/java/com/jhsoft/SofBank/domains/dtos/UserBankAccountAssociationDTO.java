package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.utils.enums.TypeRol;
import lombok.*;

@Getter
@Setter
public class UserBankAccountAssociationDTO {

//    private BankAccount bankAccount;
//    private Users users;
//    private TypeRol role;
    private String bankAccount;
    private String users;
    private String identification;
    private String numberAccount;
    private TypeRol role;
}
