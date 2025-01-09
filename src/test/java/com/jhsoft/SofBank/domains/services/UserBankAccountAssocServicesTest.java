package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.dtos.UserBankAccountAssociationDTO;
import com.jhsoft.SofBank.domains.dtos.UserBankAccountDTO;
import com.jhsoft.SofBank.domains.repositories.UserBankAccountAssocRepository;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.AssociationNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.jhsoft.SofBank.domains.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class UserBankAccountAssocServicesTest {
    @Mock
    private UserBankAccountAssocRepository userBankAccountAssocRepository;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private UsersServices usersServices;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserBankAccountAssocServices userBankAccountAssocServices;

    private UserBankAccountAssociation userBankAccountAssociation;
    private BankAccount bankAccount;
    private Users user;
    private UserBankAccountAssociationDTO userBankAccountAssociationDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear objetos de prueba
        user = new Users();
        user.setIdentification("1234");
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        bankAccount = new BankAccount("123456", 1000.0, TypeAccount.AHORRO);

        userBankAccountAssociation = new UserBankAccountAssociation(bankAccount, user, TypeRol.ADMIN);
        userBankAccountAssociation.setAssociatedBy("System");

        userBankAccountAssociationDTO = new UserBankAccountAssociationDTO();
        userBankAccountAssociationDTO.setRole(TypeRol.ADMIN);
    }

    @Test
    public void testCreateAssociation() {

        when(userBankAccountAssocRepository.findByUsersAndBankAccount(user, bankAccount)).thenReturn(Optional.empty());
        when(userBankAccountAssocRepository.save(any(UserBankAccountAssociation.class))).thenReturn(userBankAccountAssociation);

        UserBankAccountAssociation createdAssociation = userBankAccountAssocServices.createAssociation(user, bankAccount, userBankAccountAssociationDTO);

        assertNotNull(createdAssociation);
        assertEquals(user, createdAssociation.getUsers());
        assertEquals(bankAccount, createdAssociation.getBankAccount());
        assertEquals(TypeRol.ADMIN, createdAssociation.getRole());
    }

    @Test
    public void testGetAssociation_Success() {

        when(userBankAccountAssocRepository.findByUsersAndBankAccount(user, bankAccount))
                .thenReturn(Optional.of(userBankAccountAssociation));

        UserBankAccountAssociation association = userBankAccountAssocServices.getAssociation(user, bankAccount);

        assertNotNull(association);
        assertEquals(user, association.getUsers());
        assertEquals(bankAccount, association.getBankAccount());
    }

    @Test
    public void testGetAssociation_NotFound() {

        when(userBankAccountAssocRepository.findByUsersAndBankAccount(user, bankAccount))
                .thenReturn(Optional.empty());

        assertThrows(AssociationNotFoundException.class, () -> userBankAccountAssocServices.getAssociation(user, bankAccount));
    }

    @Test
    public void testDeleteAssociation() {

        when(userBankAccountAssocRepository.findByUsersAndBankAccount(user, bankAccount))
                .thenReturn(Optional.of(userBankAccountAssociation));

        userBankAccountAssocServices.deleteAssociation(user, bankAccount);

        verify(userBankAccountAssocRepository, times(1)).delete(userBankAccountAssociation);
    }

    @Test
    public void testUpdateAssociation() {

        when(userBankAccountAssocRepository.findByUsersAndBankAccount(user, bankAccount))
                .thenReturn(Optional.of(userBankAccountAssociation));
        when(bankAccountService.getAccountByNumber("987654"))
                .thenReturn(new BankAccount("987654", 2000.0, TypeAccount.CORRIENTE));
        when(userBankAccountAssocRepository.save(any(UserBankAccountAssociation.class)))
                .thenReturn(userBankAccountAssociation);

        userBankAccountAssociationDTO.setRole(TypeRol.ADMIN);
        userBankAccountAssociationDTO.setBankAccount("987654");

        UserBankAccountAssociation updatedAssociation = userBankAccountAssocServices.updateAssociation(user, bankAccount, userBankAccountAssociationDTO);

        assertNotNull(updatedAssociation);
        assertEquals(TypeRol.ADMIN, updatedAssociation.getRole());
        assertEquals("987654", updatedAssociation.getBankAccount().getNumberAccount());
    }

    @Test
    public void testGetUserBankAccountInfoByIdentification() {
        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.of(user));
        when(userBankAccountAssocRepository.findByUsers(user)).thenReturn(List.of(userBankAccountAssociation));

        List<UserBankAccountDTO> accountInfo = userBankAccountAssocServices.getUserBankAccountInfoByIdentification("1234");

        assertNotNull(accountInfo);
        assertEquals(1, accountInfo.size());
        assertEquals("123456", accountInfo.get(0).getAccountNumber());
        assertEquals(1000.0, accountInfo.get(0).getBalance());
    }
}
