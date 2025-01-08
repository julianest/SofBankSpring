package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.domains.entities.TypeAccount;
import com.jhsoft.SofBank.domains.services.TypeTransaction;
import lombok.*;


@Getter
@Setter
public class TransactionRequestDTO {

    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;


}
