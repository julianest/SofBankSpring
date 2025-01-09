package com.jhsoft.SofBank.controllers;

import com.jhsoft.SofBank.domains.dtos.BankAccountDTO;
import com.jhsoft.SofBank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.services.BankAccountService;
import com.jhsoft.SofBank.domains.services.factory.BankAccountServiceFactory;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountServiceFactory bankAccountServiceFactory;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @PostMapping
    public ResponseEntity<List<BankAccount>> createAccounts(@RequestBody List<BankAccountDTO> bankAccountDTOs){
        List<BankAccount> createdAccounts = new ArrayList<>();
        for (BankAccountDTO bankAccountDTO : bankAccountDTOs) {
            BankAccount createdAccount = bankAccountService.createAccount(bankAccountDTO);
            createdAccounts.add(createdAccount);
        }
        return ResponseEntity.ok(createdAccounts);
    }

    @GetMapping("/{numberAccount}")
    public ResponseEntity<BankAccount> getAccountByNumber(@PathVariable String numberAccount ){
        BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);
            return ResponseEntity.ok(bankAccount);

    }

    @DeleteMapping("/delete/{numberAccount}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String numberAccount){
        try{
            bankAccountService.deleteAccount(numberAccount);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.noContent().build();
        }

    }

    @PutMapping("/update/{numberAccount}")
    public ResponseEntity<BankAccount> updateAccount(@PathVariable String numberAccount,
                                                     @RequestBody TransactionRequestDTO requestDTO ){

        BankAccountService bankAccountService = bankAccountServiceFactory.getService(requestDTO.getTypeAccount());
        BankAccount bankAccount = bankAccountService.updateBalance(numberAccount, requestDTO);
        return ResponseEntity.ok(bankAccount);
    }
}
