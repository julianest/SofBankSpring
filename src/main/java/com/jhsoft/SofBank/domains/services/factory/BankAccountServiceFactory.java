package com.jhsoft.SofBank.domains.services.factory;

import com.jhsoft.SofBank.domains.entities.TypeAccount;
import com.jhsoft.SofBank.domains.services.BankAccountService;
import com.jhsoft.SofBank.domains.services.CheckingAccountService;
import com.jhsoft.SofBank.domains.services.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BankAccountServiceFactory {

    @Autowired
    @Qualifier("savingsAccountService")
    private SavingsAccountService savingsAccountService;

    @Autowired
    @Qualifier("checkingAccountService")
    private CheckingAccountService checkingAccountService;

    public BankAccountService getService(TypeAccount typeAccount) {
        switch (typeAccount) {
            case AHORRO:
                return savingsAccountService;
            case CORRIENTE:
                return checkingAccountService;
            default:
                throw new IllegalArgumentException("Tipo de cuenta no reconocido: " + typeAccount);
        }
    }
}
