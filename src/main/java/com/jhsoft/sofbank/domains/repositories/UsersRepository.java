package com.jhsoft.sofbank.domains.repositories;

import com.jhsoft.sofbank.domains.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends  JpaRepository<Users, Long> {

    Optional<Users> findByIdentification(String identification);
}
