package com.jhsoft.SofBank.domains.dtos;

import com.jhsoft.SofBank.utils.enums.TypeAccount;
import com.jhsoft.SofBank.utils.enums.TypeTransaction;
import lombok.*;


@Getter
@Setter
public class TransactionRequestDTO {

    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;

}
