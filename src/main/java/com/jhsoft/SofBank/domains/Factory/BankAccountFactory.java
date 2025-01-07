package com.jhsoft.SofBank.domains.Factory;

import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.CheckingAccount;
import com.jhsoft.SofBank.domains.entities.SavingsAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFactory implements IBankAccountFactory{
    @Override
    public BankAccount createAccount(String numberAccount, double balance, double rateInterest, TypeAccount typeAccount){
     switch (typeAccount){
         case AHORRO:
             return new SavingsAccount(numberAccount,balance,rateInterest);
         case CORRIENTE:
             return new CheckingAccount(numberAccount,balance,rateInterest);
         default:
             throw new IllegalArgumentException("El tipo de cuenta no coincide con las parametrizadas");
     }
    }
}
