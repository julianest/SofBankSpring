package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.utils.enums.TypeRol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jhsoft.SofBank.domains.dtos.UserBankAccountDTO;
import com.jhsoft.SofBank.domains.dtos.UserBankAccountAssociationDTO;
import com.jhsoft.SofBank.domains.entities.BankAccount;
import com.jhsoft.SofBank.domains.entities.UserBankAccountAssociation;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.repositories.UserBankAccountAssocRepository;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.AccountNotFoundException;
import com.jhsoft.SofBank.exceptions.AssociationNotFoundException;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserBankAccountAssocServices {

    @Autowired
    private UserBankAccountAssocRepository userBankAccountAssocRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserBankAccountAssocServices.class);

    public UserBankAccountAssociation getAssociation(Users users, BankAccount account) {
        if (users == null) {
            throw new UsersNotFoundException("Usuario no encontrado.");
        }
        if (account == null) {
            throw new AccountNotFoundException("Cuenta no encontrada.");
        }
        return userBankAccountAssocRepository.findByUsersAndBankAccount(users, account)
                .orElseThrow(() -> new AssociationNotFoundException(
                        "Asociación no encontrada entre el usuario con identificación: "
                                + users.getIdentification() + " y la cuenta: " + account.getNumberAccount()));
    }

    public UserBankAccountAssociation createAssociation(Users users, BankAccount account,
                                                        UserBankAccountAssociationDTO userBankAccountAssociationDTO){

        Optional<UserBankAccountAssociation> existingAssociation = userBankAccountAssocRepository.findByUsersAndBankAccount(users, account);

        /* validacioin de si la asociacion ya existe se comenta para poder generar union entre otras cuentas.
        if (existingAssociation.isPresent()) {
            // Si la asociación ya existe, lanzamos una excepción o retornamos un mensaje indicando que ya existe
            throw new AssociationNotFoundException("La asociación entre el usuario con identificación: "
                    + users.getIdentification() + " y la cuenta: " + account.getNumberAccount() + " ya existe.");
        }*/
        UserBankAccountAssociation createdAssociation = new UserBankAccountAssociation(
                account ,users, userBankAccountAssociationDTO.getRole());

        createdAssociation.setAssociatedBy("AssociationSystem");
        logger.info("Creando la asociacion del usuario: "+ createdAssociation.getUsers()
                + " y la cuenta: " + createdAssociation.getBankAccount());
        UserBankAccountAssociation savedAssociation =  userBankAccountAssocRepository.save(createdAssociation);
        return savedAssociation;
    }

    public void deleteAssociation(Users users, BankAccount account){
        UserBankAccountAssociation association = getAssociation(users,account);
        //UserBankAccountAssociation associationToDelete = userBankAccountAssocRepository.findByIdentification(association.getIdUserBankAccountAssoc()).
               // orElseThrow(()-> new AssociationNotFoundException("Asociacion no Encontrada para eliminar"));
        userBankAccountAssocRepository.delete(association);
        logger.info("Eliminacion de la asociacion con id:  "+ association.getIdUserBankAccountAssoc());
    }

    @Transactional
    public UserBankAccountAssociation updateAssociation(Users users, BankAccount account,
                                                        UserBankAccountAssociationDTO userBankAccountAssociationDTO){

        UserBankAccountAssociation association = getAssociation(users,account);

        if (userBankAccountAssociationDTO.getBankAccount() != null) {
            BankAccount newBankAccount = bankAccountService.getAccountByNumber(userBankAccountAssociationDTO.getBankAccount());
            association.setBankAccount(newBankAccount);
        }

        if (userBankAccountAssociationDTO.getUsers() != null) {
            Users newUser = usersServices.getUserByIdentification(userBankAccountAssociationDTO.getUsers());
            association.setUsers(newUser);
        }

        if (userBankAccountAssociationDTO.getRole() != null) {
            association.setRole(TypeRol.valueOf(String.valueOf(userBankAccountAssociationDTO.getRole())));
        }
        UserBankAccountAssociation updatedAssociation = userBankAccountAssocRepository.save(association);
        logger.info("Asociacion modificado con id:  "+ updatedAssociation.getIdUserBankAccountAssoc());
        return updatedAssociation;
    }

    public List<UserBankAccountDTO> getUserBankAccountInfoByIdentification(String identification) {

        Users user = usersRepository.findByIdentification(identification)
                .orElseThrow(() -> new UsersNotFoundException("Usuario no encontrado con identificación: " + identification));

        List<UserBankAccountAssociation> associations = userBankAccountAssocRepository.findByUsers(user);

        if (associations.isEmpty()) {
            throw new AssociationNotFoundException("No hay cuentas asociadas para el usuario con identificación: " + identification);
        }

        return associations.stream()
                .map(association -> new UserBankAccountDTO(
                        user.getIdentification(),
                        user.getName(),
                        user.getLastName(),
                        user.getEmail(),
                        association.getBankAccount().getNumberAccount(),
                        association.getBankAccount().getBalance(),
                        association.getBankAccount().getTypeAccount()
                ))
                .collect(Collectors.toList());
    }

}
