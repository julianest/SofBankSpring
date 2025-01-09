package com.jhsoft.SofBank.controllers;

import com.jhsoft.SofBank.domains.dtos.UserBankAccountAssociationDTO;
import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.UserBankAccountAssociation;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.services.BankAccountService;
import com.jhsoft.SofBank.domains.services.UserBankAccountAssocServices;
import com.jhsoft.SofBank.domains.services.UsersServices;
import com.jhsoft.SofBank.domains.services.factory.BankAccountServiceFactory;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersbankaccount")
public class UserBankAccountAssocController {

    @Autowired
    private UserBankAccountAssocServices userBankAccountAssocServices;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/{identification}/{numberAccount}")
    public ResponseEntity<UserBankAccountAssociation> createAssociation(
            @PathVariable String identification,
            @PathVariable String numberAccount,
            @RequestBody UserBankAccountAssociationDTO userBankAccountAssociationDTO){
        Users userAccount = usersServices.getUserByIdentification(identification);
        BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);

        UserBankAccountAssociation createdAssociation = userBankAccountAssocServices.createAssociation(userAccount,bankAccount,userBankAccountAssociationDTO);
        return ResponseEntity.ok(createdAssociation);
    }

    @GetMapping("/{identification}/{numberAccount}")
    public ResponseEntity<UserBankAccountAssociation> getAssociation(
            @PathVariable String identification, @PathVariable String numberAccount ){

        Users userAccount = usersServices.getUserByIdentification(identification);
        BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);

        UserBankAccountAssociation association = userBankAccountAssocServices.getAssociation(userAccount,bankAccount);
        return ResponseEntity.ok(association);
    }

    @DeleteMapping("/delete/{identification}/{numberAccount}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable String identification, @PathVariable String numberAccount){
        try{
            Users userAccount = usersServices.getUserByIdentification(identification);
            BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);
            userBankAccountAssocServices.deleteAssociation(userAccount,bankAccount);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/update/{identification}/{numberAccount}")
    public ResponseEntity<UserBankAccountAssociation> updateAssociation(
                @PathVariable String identification, @PathVariable String numberAccount,
                @RequestBody UserBankAccountAssociationDTO userBankAccountAssociationDTO ){
        Users userAccount = usersServices.getUserByIdentification(identification);
        BankAccount bankAccount = bankAccountService.getAccountByNumber(numberAccount);
        UserBankAccountAssociation associationModify = userBankAccountAssocServices.updateAssociation(userAccount,bankAccount, userBankAccountAssociationDTO);
        return ResponseEntity.ok(associationModify);
    }

    @GetMapping("/user/{identification}")
    public ResponseEntity<List<UserBankAccountDTO>> getUserBankAccountInfo(@PathVariable String identification) {
        List<UserBankAccountDTO> userBankAccounts = userBankAccountAssocServices.getUserBankAccountInfoByIdentification(identification);
        return ResponseEntity.ok(userBankAccounts);
    }
}
