package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.entities.CheckingAccount;
import com.jhsoft.SofBank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService extends BankAccountService{

    @Autowired
    @Qualifier("checkingAccountStrategy")
    private ICalculationInterestStrategy strategyCalcInterest;


    public void deposit(CheckingAccount bankAccount, double amount){
        super.deposit(bankAccount, amount);
        applyRateInterest(bankAccount);
    }

    public void applyRateInterest(CheckingAccount bankAccount){
        double interest = strategyCalcInterest.calculationInterest(bankAccount.getBalance(),bankAccount.getRateInterest());
        bankAccount.setInterestAccumulated(interest);
        bankAccount.setBalance(bankAccount.getBalance()+interest);
    }

}
