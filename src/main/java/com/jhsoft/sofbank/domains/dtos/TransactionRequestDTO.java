package com.jhsoft.sofbank.domains.dtos;

import com.jhsoft.sofbank.utils.enums.TypeAccount;
import com.jhsoft.sofbank.utils.enums.TypeTransaction;
import lombok.*;


@Getter
@Setter
public class TransactionRequestDTO {

    private double amount;
    private TypeTransaction typeTransaction;
    private TypeAccount typeAccount;

}
