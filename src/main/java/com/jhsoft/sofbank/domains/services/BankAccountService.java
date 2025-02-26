package com.jhsoft.sofbank.domains.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jhsoft.sofbank.domains.Factory.BankAccountFactory;
import com.jhsoft.sofbank.domains.dtos.BankAccountDTO;
import com.jhsoft.sofbank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.sofbank.domains.entities.BankAccount;
import com.jhsoft.sofbank.domains.repositories.BankAccountRepository;
import com.jhsoft.sofbank.exceptions.AccountNotFoundException;
import com.jhsoft.sofbank.utils.Converters;
import com.jhsoft.sofbank.utils.enums.TypeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BankAccountService {

    @Autowired
    private BankAccountFactory bankAccountFactory;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Converters converters;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);


    public BankAccount createAccount(BankAccountDTO bankAccountDTO){

        BankAccount bankAccount = (BankAccount) authenticationService.addCreatedByAuditInfo().apply(
                (BankAccount) converters.dtoToObject(bankAccountDTO, TypeObject.BANKACCOUNT));

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        logger.info("Creando cuenta "+ bankAccountDTO.getTypeAccount() + " N° " + bankAccountDTO.getNumberAccount());
        return savedAccount;
    }

    public BankAccount getAccountByNumber(String numberAccount){
        return bankAccountRepository.findByNumberAccount(numberAccount).
                orElseThrow(()-> new AccountNotFoundException("Cuenta no Encontrada con Numero: "+ numberAccount)) ;
    }

    public void deleteAccount(String numberAccount){
        BankAccount bankAccount = bankAccountRepository.findByNumberAccount(numberAccount).
                orElseThrow(()-> new AccountNotFoundException("Cuenta no Encontrada para eliminar con Numero: "+ numberAccount));
        bankAccountRepository.delete(bankAccount);
        logger.info("Eliminacion cuenta "+ bankAccount.getTypeAccount() + " N° " + bankAccount.getNumberAccount());

    }

    public abstract BankAccount updateBalance(String numberAccount, TransactionRequestDTO requestDTO);

    public BankAccount deposit(BankAccount bankAccount, double amount){
        if(amount > 0){
            bankAccount.setBalance(bankAccount.getBalance() + amount);
            //bankAccountRepository.save(bankAccount);
            logger.info("Se ha depositado "+ amount + " En la cuenta: "+ bankAccount.getNumberAccount() );
        }else {
            logger.warn("Intento de depósito con un monto no válido: " + amount);
        }
        return bankAccount;
    }


    public BankAccount withdraw(BankAccount bankAccount, double amount){
        if(amount <= bankAccount.getBalance()){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            //bankAccountRepository.save(bankAccount);
            logger.info("- Retiro   de $" + amount + " en la cuenta "+ bankAccount.getNumberAccount());
        }else{
            logger.warn("- Imposible retirar. el monto supera el saldo en cuenta: $" + bankAccount.getBalance());
        }
        return bankAccount;
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
