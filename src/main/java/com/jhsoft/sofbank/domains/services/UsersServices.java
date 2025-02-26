package com.jhsoft.sofbank.domains.services;

import com.jhsoft.sofbank.utils.Converters;
import com.jhsoft.sofbank.utils.LoggerCentralized;
import com.jhsoft.sofbank.utils.enums.TypeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.jhsoft.sofbank.domains.dtos.UsersDTO;
import com.jhsoft.sofbank.domains.entities.Users;
import com.jhsoft.sofbank.domains.repositories.UsersRepository;
import com.jhsoft.sofbank.exceptions.UsersNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
