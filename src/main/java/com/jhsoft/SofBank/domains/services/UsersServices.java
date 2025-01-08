package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.dtos.TransactionRequestDTO;
import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {

    @Autowired
    private UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);


    public Users createUser(UsersDTO usersDTO){
        Users createdUser = new Users(usersDTO.getIdentification(),usersDTO.getName(), usersDTO.getLastName(), usersDTO.getEmail());
        createdUser.setCreatedBy("UserSystem");
        logger.info("Creando el usuario con id: "+ usersDTO.getIdentification());
        Users savedUser =  usersRepository.save(createdUser);
        return savedUser;
    }

    public Users getUserByIdentification(String identification){
        return usersRepository.findByIdentification(identification).
                orElseThrow(()-> new UsersNotFoundException("Usuario no Encontrado con id: "+ identification)) ;
    }

    public void deleteUser(String identification){
        Users userToDelete = usersRepository.findByIdentification(identification).
                orElseThrow(()-> new UsersNotFoundException("Cuenta no Encontrada para eliminar con Numero: "+ identification));
        usersRepository.delete(userToDelete);
        logger.info("Eliminacion Usuario con id:  "+ userToDelete.getIdentification() );

    }

    @Transactional
    public Users updateUser(String identification, UsersDTO usersDTO){
        Users userModify = usersRepository.findByIdentification(identification)
                .orElseThrow(()-> new UsersNotFoundException("Usuario No encontrado para actualizar Id: " + identification));

        userModify.setIdentification(usersDTO.getIdentification() != null ? usersDTO.getIdentification() : userModify.getIdentification());
        userModify.setName(usersDTO.getName() != null ? usersDTO.getName() : userModify.getName());
        userModify.setLastName(usersDTO.getLastName() != null ? usersDTO.getLastName() : userModify.getLastName());
        userModify.setEmail(usersDTO.getEmail() != null ? usersDTO.getEmail() : userModify.getEmail());

        Users updatedUser = usersRepository.save(userModify);
        logger.info("Usuario modificado con id:  "+ userModify.getIdentification());
        usersRepository.save(userModify);
        return userModify;
    }

}
