package com.jhsoft.sofbank.domains.services.factory;

import com.jhsoft.sofbank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.sofbank.domains.entities.BankAccount;

public interface IBankAccountService {
    BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO);
}