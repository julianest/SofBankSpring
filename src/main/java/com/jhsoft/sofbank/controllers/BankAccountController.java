package com.jhsoft.sofbank.controllers;

import com.jhsoft.sofbank.domains.dtos.BankAccountDTO;
import com.jhsoft.sofbank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.sofbank.domains.entities.BankAccount;
import com.jhsoft.sofbank.domains.services.AuthenticationService;
import com.jhsoft.sofbank.domains.services.BankAccountService;
import com.jhsoft.sofbank.domains.services.factory.BankAccountServiceFactory;
import com.jhsoft.sofbank.exceptions.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountServiceFactory bankAccountServiceFactory;

    @Autowired
    private AuthenticationService authenticationService;

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

    @PutMapping("/update/fromMicros/{numberAccount}")
    public ResponseEntity<BankAccount> updateAccountFromMicroservice(@RequestHeader HttpHeaders headersRequest, @PathVariable String numberAccount,
                                                     @RequestBody TransactionRequestDTO requestDTO ){
        String authName = headersRequest.getFirst(HttpHeaders.AUTHORIZATION);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authName);  // Agrega el header Authorization
        headers.setContentType(MediaType.APPLICATION_JSON);  // Si es necesario, especifica el tipo de contenido

        // Crear la entidad con los headers y el cuerpo de la solicitud
        HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        // Hacer la solicitud POST al microservicio
        String url = "http://localhost:8085/transact/process/" + numberAccount;  // Dirección del microservicio
        TransactionRequestDTO transactionResponse = restTemplate.exchange(url, HttpMethod.POST, entity, TransactionRequestDTO.class).getBody();

        BankAccountService bankAccountService = bankAccountServiceFactory.getService(requestDTO.getTypeAccount());
        BankAccount bankAccount = bankAccountService.updateBalance(numberAccount, requestDTO);
        return ResponseEntity.ok(bankAccount);
    }
}
