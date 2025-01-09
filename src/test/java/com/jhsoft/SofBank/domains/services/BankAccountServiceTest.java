package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.Factory.BankAccountFactory;
import com.jhsoft.SofBank.domains.dtos.BankAccountDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.TypeAccount;
import com.jhsoft.SofBank.domains.repositories.BankAccountRepository;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountFactory bankAccountFactory;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {

        BankAccountDTO accountDTO = new BankAccountDTO("1234", 500.0, 0.05, TypeAccount.AHORRO);

        BankAccount expectedAccount = new BankAccount("1234", 500.0, TypeAccount.AHORRO);

        when(bankAccountFactory.createAccount(anyString(), anyDouble(), anyDouble(), any())).thenReturn(expectedAccount);

        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(expectedAccount);

        BankAccount createdAccount = bankAccountService.createAccount(accountDTO);

        assertNotNull(createdAccount);
        assertEquals("1234", createdAccount.getNumberAccount());
        assertEquals(500.0, createdAccount.getBalance());
        assertEquals(TypeAccount.AHORRO, createdAccount.getTypeAccount());

        verify(bankAccountFactory, times(1)).createAccount(anyString(), anyDouble(), anyDouble(), any());
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    public void testGetAccountByNumber_Success() {

        BankAccount account = new BankAccount("1234", 500.0, TypeAccount.CORRIENTE);

        when(bankAccountRepository.findByNumberAccount("1234")).thenReturn(Optional.of(account));

        BankAccount foundAccount = bankAccountService.getAccountByNumber("1234");

        assertNotNull(foundAccount);
        assertEquals("1234", foundAccount.getNumberAccount());
        assertEquals(500.0, foundAccount.getBalance());
        assertEquals(TypeAccount.CORRIENTE, foundAccount.getTypeAccount());
    }

    @Test
    public void testGetAccountByNumber_AccountNotFound() {

        when(bankAccountRepository.findByNumberAccount("1234")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> bankAccountService.getAccountByNumber("1234"));
    }

    @Test
    public void testDeposit_Success() {

        BankAccount account = new BankAccount("1234", 500.0, TypeAccount.AHORRO);

        BankAccount updatedAccount = bankAccountService.deposit(account, 100.0);

        assertNotNull(updatedAccount);
        assertEquals(600.0, updatedAccount.getBalance());
    }

    @Test
    public void testWithdraw_Success() {

        BankAccount account = new BankAccount("1234", 500.0, TypeAccount.AHORRO);

        BankAccount updatedAccount = bankAccountService.withdraw(account, 100.0);

        assertNotNull(updatedAccount);
        assertEquals(400.0, updatedAccount.getBalance());
    }

    @Test
    public void testWithdraw_InsufficientFunds() {

        BankAccount account = new BankAccount("1234", 500.0, TypeAccount.AHORRO);

        BankAccount updatedAccount = bankAccountService.withdraw(account, 600.0);

        assertNotNull(updatedAccount);
        assertEquals(500.0, updatedAccount.getBalance());
    }

    @Test
    public void testDeleteAccount_Success() {

        BankAccount account = new BankAccount("1234", 500.0, TypeAccount.CORRIENTE);

        when(bankAccountRepository.findByNumberAccount("1234")).thenReturn(Optional.of(account));

        bankAccountService.deleteAccount("1234");

        verify(bankAccountRepository, times(1)).delete(account);
    }

    @Test
    public void testDeleteAccount_AccountNotFound() {

        when(bankAccountRepository.findByNumberAccount("1234")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> bankAccountService.deleteAccount("1234"));
    }
}
