package com.jhsoft.sofbank.domains.repositories;

import com.jhsoft.sofbank.domains.entities.BankAccount;
import com.jhsoft.sofbank.domains.entities.UserBankAccountAssociation;
import com.jhsoft.sofbank.domains.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBankAccountAssocRepository extends JpaRepository<UserBankAccountAssociation, Long> {

    Optional<UserBankAccountAssociation> findByUsersAndBankAccount(Users users, BankAccount bankAccount);

    List<UserBankAccountAssociation> findByUsers(Users users);

    /*@Query("SELECT new com.jhsoft.SofBank.dtos.UserBankAccountInfoDTO(u.name, " +
            "       (SELECT b.numberAccount FROM UserBankAccountAssociation uba " +
            "        JOIN uba.bankAccount b WHERE uba.users.idUsers = u.idUsers)) " +
            "FROM UserBankAccountAssociation uba " +
            "JOIN uba.users u " +
            "WHERE u.idUsers = :userId")
    UserBankAccountDTO findUserBankAccountInfoByUserId(@Param("userId") Long userId);*/
}
