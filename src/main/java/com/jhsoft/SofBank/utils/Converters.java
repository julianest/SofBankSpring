package com.jhsoft.SofBank.utils;

import com.jhsoft.SofBank.domains.Factory.BankAccountFactory;
import com.jhsoft.SofBank.domains.dtos.BankAccountDTO;
import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.utils.enums.TypeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converters {

    @Autowired
    private BankAccountFactory bankAccountFactory;

    public Object dtoToObject(Object object, TypeObject typeObject ){

        switch (typeObject){
            case USERS:
                UsersDTO usersDTO = (UsersDTO) object;
                return new Users(usersDTO.getIdentification(), usersDTO.getName(), usersDTO.getLastName(), usersDTO.getEmail());
            case BANKACCOUNT:
                BankAccountDTO bankAccountDTO = (BankAccountDTO) object;
                return bankAccountFactory.createAccount(
                        bankAccountDTO.getNumberAccount(), bankAccountDTO.getBalance(),
                        bankAccountDTO.getRateInterest(), bankAccountDTO.getTypeAccount()
                );
             //POr ajustar las demas entidades aqui teniendo en cuenta un solo DTO para cada clase.
            default:
                throw new IllegalArgumentException("El tipo de Objeto no coincide con los parametrizados");
        }
    }
}
