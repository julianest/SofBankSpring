package com.jhsoft.SofBank.domains.repositories;

import com.jhsoft.SofBank.domains.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByNumberAccount(String numberAccount);
}
