package com.jhsoft.SofBank.domains.services.factory;

import com.jhsoft.SofBank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;

public interface IBankAccountService {
    BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO);
}