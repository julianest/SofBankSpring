package com.jhsoft.SofBank.domains.repositories;

import com.jhsoft.SofBank.domains.entities.UserLogin;
import com.jhsoft.SofBank.domains.services.TypeRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);

    /*@Query("SELECT u FROM UserLogin u JOIN u.roles r WHERE r.typeRol = :typeRol")
    Optional<UserLogin> findByTypeRol(@Param("typeRol") TypeRol typeRol);*/
}
