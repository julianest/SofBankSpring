package com.jhsoft.sofbank.domains.repositories;

import com.jhsoft.sofbank.domains.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);

    /*@Query("SELECT u FROM UserLogin u JOIN u.roles r WHERE r.typeRol = :typeRol")
    Optional<UserLogin> findByTypeRol(@Param("typeRol") TypeRol typeRol);*/
}
