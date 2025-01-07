package com.jhsoft.SofBank.controllers;

import com.jhsoft.SofBank.domains.dtos.BankAccountDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccountDTO bankAccountDTO){
        BankAccount createdAccount = bankAccountService.createAccount(bankAccountDTO);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{numberAccount}")
    public ResponseEntity<BankAccount> getAccountByNumber(@PathVariable String numberAccount ){
        Optional<BankAccount> bankAccount = bankAccountService.getAccountByNumber(numberAccount);
        if (bankAccount.isPresent()) {
            return ResponseEntity.ok(bankAccount.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }


//    @PutMapping("/{numberAccount}/deposit ")
//    public ResponseEntity<BankAccount> deposit(@PathVariable String numberAccount, @RequestParam double amount ){
////        BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);
////        if(bankAccount == null ){
////            return ResponseEntity.notFound().build();
////        }
//        Optional<BankAccount> bankAccount = bankAccountService.getAccountByNumber(numberAccount);
//        if (bankAccount.isPresent()) {
//            return ResponseEntity.ok(bankAccount.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//        bankAccountService.deposit(bankAccount, amount);
//        return ResponseEntity.ok(bankAccount);
//    }
}
