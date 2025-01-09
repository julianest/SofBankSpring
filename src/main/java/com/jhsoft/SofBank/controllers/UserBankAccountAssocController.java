package com.jhsoft.SofBank.controllers;

import com.jhsoft.SofBank.domains.dtos.UserBankAccountAssociationDTO;
import com.jhsoft.SofBank.domains.dtos.UserBankAccountDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.UserBankAccountAssociation;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.services.BankAccountService;
import com.jhsoft.SofBank.domains.services.UserBankAccountAssocServices;
import com.jhsoft.SofBank.domains.services.UsersServices;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/batch")
    public ResponseEntity<List<UserBankAccountAssociation>> createAssociations(
            @RequestBody List<UserBankAccountAssociationDTO> associationsDTO){

        List<UserBankAccountAssociation> createdAssociations = new ArrayList<>();

        for (UserBankAccountAssociationDTO associationDTO : associationsDTO) {

            Users userAccount = usersServices.getUserByIdentification(associationDTO.getIdentification());

            // Busca la cuenta bancaria por su número de cuenta
            BankAccount bankAccount = bankAccountService.getAccountByNumber(associationDTO.getNumberAccount());

            // Asegúrate de que el usuario y la cuenta bancaria fueron encontrados
            if (userAccount == null) {
                throw new IllegalArgumentException("Usuario con identificación " + associationDTO.getIdentification() + " no encontrado");
            }
            if (bankAccount == null) {
                throw new IllegalArgumentException("Cuenta bancaria con número " + associationDTO.getNumberAccount() + " no encontrada");
            }

            UserBankAccountAssociation createdAssociation = userBankAccountAssocServices.createAssociation(userAccount, bankAccount, associationDTO);
            createdAssociations.add(createdAssociation);
        }
        return ResponseEntity.ok(createdAssociations);
    }


    @PostMapping("/{identification}/{numberAccount}")
    public ResponseEntity<UserBankAccountAssociation> createAssociations(
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
