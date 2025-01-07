package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.Factory.BankAccountFactory;
import com.jhsoft.SofBank.domains.dtos.BankAccountDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.CheckingAccount;
import com.jhsoft.SofBank.domains.entities.SavingsAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;
import com.jhsoft.SofBank.domains.repositories.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountFactory bankAccountFactory;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);


    public BankAccount createAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = bankAccountFactory.createAccount(
                    bankAccountDTO.getNumberAccount(), bankAccountDTO.getBalance(),
                    bankAccountDTO.getRateInterest(), bankAccountDTO.getTypeAccount()
        );
        bankAccount.setCreatedBy("Sistema");
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

        logger.info("Creando cuenta "+ bankAccountDTO.getTypeAccount() + " N° " + bankAccountDTO.getNumberAccount());
        return savedAccount;
    }

    public Optional<BankAccount> getAccountByNumber(String numberAccount){
//        BankAccount bankAccount =  bankAccountRepository.findByNumberAccount(numberAccount) ;
//        if(bankAccount == null){
//            throw new RuntimeException("No se encuentra la cuenta con este numero: "+ bankAccount.getNumberAccount());
//        }
//        return bankAccount;
        return bankAccountRepository.findByNumberAccount(numberAccount) ;
    }



    @Transactional
    public void deposit(BankAccount bankAccount, double amount){
        if(amount > 0){
            bankAccount.setBalance(bankAccount.getBalance() + amount);
            logger.info("Se ha depositado "+ amount + " En la cuenta: "+ bankAccount.getNumberAccount() );
        }else {
            logger.warn("Intento de depósito con un monto no válido: " + amount);
        }
    }

    @Transactional
    public double withdraw(BankAccount bankAccount, double amount){
        if(amount <= bankAccount.getBalance()){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            logger.info("- Retiro   de $" + amount + " en la cuenta "+ bankAccount.getNumberAccount());
            return amount;
        }else{
            logger.info("- Imposible retirar. el monto supera el saldo en cuenta: $" + bankAccount.getBalance());
            return 0.0;
        }
    }

    public double getInterestFreeBalance(BankAccount bankAccount){ return bankAccount.getBalance() - bankAccount.getInterestAccumulated();}

    public void showAccountStatement(BankAccount bankAccount){
        String message =
                String.format(
                        "|----------------------------------|\n" +
                                " - Cuenta:              N° %s\n" +
                                " - Tipo:                %s\n" +
                                " - Saldo Sin intereses: $%.2f\n" +
                                " - Interés Acumulado:   $%.2f\n" +
                                " - Total total:         $%.2f\n" +
                                "|----------------------------------| ",
                        bankAccount.getNumberAccount(), bankAccount.getTypeAccount(), getInterestFreeBalance(bankAccount),
                        bankAccount.getInterestAccumulated(), bankAccount.getBalance());
        System.out.println(message);
    }

}
