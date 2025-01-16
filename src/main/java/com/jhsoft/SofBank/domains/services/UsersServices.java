package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.utils.Converters;
import com.jhsoft.SofBank.utils.LoggerCentralized;
import com.jhsoft.SofBank.utils.enums.TypeObject;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Service
public class UsersServices {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Converters converters;

    @Autowired
    private LoggerCentralized loggerCentralized;

    private static final Logger logger = LoggerFactory.getLogger(UsersServices.class);

    public Mono<Users> createUser(UsersDTO usersDTO) {
        return Mono.fromCallable(() -> {
                    String authUser = authenticationService.getCurrentUser();
                    Users createdUser = (Users) Optional.ofNullable(converters.dtoToObject(usersDTO, TypeObject.USERS))
                            .map(user -> authenticationService.addCreatedByAuditInfo().apply(user))
                            .orElseThrow(() -> new IllegalArgumentException("dato invalido"));

                    return usersRepository.save(createdUser);
                })
                .flatMap(user -> loggerCentralized.logInsert(Users.class, user));
    }

    public Users getUserByIdentification(String identification){
        return usersRepository.findByIdentification(identification).
                orElseThrow(()-> new UsersNotFoundException("Usuario no Encontrado con id: "+ identification)) ;
    }

    public void deleteUser(String identification){
        Users userToDelete = usersRepository.findByIdentification(identification).
                orElseThrow(()-> new UsersNotFoundException("Cuenta no Encontrada para eliminar con Numero: "+ identification));
        usersRepository.delete(userToDelete);
        loggerCentralized.logDelete(Users.class, userToDelete);
        //logger.info("Eliminacion Usuario con id:  "+ userToDelete.getIdentification() );

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
        loggerCentralized.logUpdate(Users.class, updatedUser);
        //logger.info("Usuario modificado con id:  "+ userModify.getIdentification());
        usersRepository.save(userModify);
        return userModify;
    }

}
