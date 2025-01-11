package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.SavingsAccount;
import com.jhsoft.SofBank.domains.repositories.BankAccountRepository;
import com.jhsoft.SofBank.domains.services.factory.IBankAccountService;
import com.jhsoft.SofBank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import com.jhsoft.SofBank.utils.enums.TypeTransaction;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("savingsAccountService")
@Primary
//@Qualifier(value = "savingsAccountService")
public class SavingsAccountService extends BankAccountService implements IBankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    @Qualifier("savingsAccountStrategy")
    private ICalculationInterestStrategy strategyCalcInterest;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @Override
    @Transactional
    public BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO){
        BankAccount bankAccount = bankAccountRepository.findByNumberAccount(numberAccount)
                .orElseThrow(()-> new AccountNotFoundException("Cuenta no encontrada para actualizar N°: " + numberAccount));

        TypeTransaction typeTransaction = requestDTO.getTypeTransaction();
        double amount = requestDTO.getAmount();

        switch (typeTransaction){
            case DEPOSIT:
                logger.info("Realizando depósito...");
                this.deposit((SavingsAccount)bankAccount, amount);
                break;
            case WITHDRAW:
                logger.info("Realizando retiro...");
                this.withdraw((SavingsAccount)bankAccount, amount);
                break;
            default:
                logger.error("Tipo de transacción no reconocido: " + typeTransaction);
                throw new IllegalArgumentException("El tipo de transaction no coincide con las parametrizadas: " + typeTransaction);

        }
        return bankAccount;
    }

    @Transactional
    public void deposit(SavingsAccount bankAccount, double amount){
        logger.debug("Ingresando al método deposit de SavingsAccountService con cuenta: " + bankAccount.getNumberAccount());
        super.deposit(bankAccount, amount);
        applyRateInterest(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public void withdraw(SavingsAccount bankAccount, double amount){
        super.withdraw(bankAccount, amount);
        bankAccountRepository.save(bankAccount);
    }

    public void applyRateInterest(SavingsAccount bankAccount){
        logger.debug("appluRateInterest de SavingsAccountService");
        double interest = strategyCalcInterest.calculationInterest(bankAccount.getBalance(),bankAccount.getRateInterest());
        bankAccount.setInterestAccumulated(interest);
        bankAccount.setBalance(bankAccount.getBalance()+interest);
        bankAccountRepository.save(bankAccount);
    }

}
