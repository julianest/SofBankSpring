package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.entities.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

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
