package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.CheckingAccount;
import com.jhsoft.SofBank.domains.entities.SavingsAccount;
import com.jhsoft.SofBank.domains.repositories.BankAccountRepository;
import com.jhsoft.SofBank.domains.services.factory.IBankAccountService;
import com.jhsoft.SofBank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import com.jhsoft.SofBank.utils.enums.TypeTransaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("checkingAccountService")
public class CheckingAccountService extends BankAccountService implements IBankAccountService  {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    @Qualifier("checkingAccountStrategy")
    private ICalculationInterestStrategy strategyCalcInterest;

    @Override
    public BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO){
        BankAccount bankAccount = bankAccountRepository.findByNumberAccount(numberAccount)
                .orElseThrow(()-> new AccountNotFoundException("Cuenta no encontrada para actualizar N°: " + numberAccount));

        TypeTransaction typeTransaction = requestDTO.getTypeTransaction();
        double amount = requestDTO.getAmount();

        switch (typeTransaction){
            case DEPOSIT:
                this.deposit((CheckingAccount) bankAccount, amount);
                break;
            case WITHDRAW:
                this.withdraw(bankAccount, amount);
                break;
            default:
                throw new IllegalArgumentException("El tipo de transaction no coincide con las parametrizadas");
        }
        return bankAccount;
    }


    @Transactional
    public void deposit(CheckingAccount bankAccount, double amount){
        super.deposit(bankAccount, amount);
        applyRateInterest(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public void withdraw(SavingsAccount bankAccount, double amount){
        super.withdraw(bankAccount, amount);
        bankAccountRepository.save(bankAccount);
    }

    public void applyRateInterest(CheckingAccount bankAccount){
        double interest = strategyCalcInterest.calculationInterest(bankAccount.getBalance(),bankAccount.getRateInterest());
        bankAccount.setInterestAccumulated(interest);
        bankAccount.setBalance(bankAccount.getBalance()+interest);
    }

}
