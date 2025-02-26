package com.jhsoft.sofbank.config;

import com.jhsoft.sofbank.domains.entities.UserLogin;
import com.jhsoft.sofbank.domains.repositories.UserLoginRepository;
import com.jhsoft.sofbank.utils.enums.TypeRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserLoginRepository userLoginRepository;

    private final PasswordEncoder passwordEncoder;
    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userLoginRepository.findByUsername("admin").isEmpty()) {
            UserLogin admin = new UserLogin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("a"));
            admin.setEnabled(true);
            admin.setTypeRol(TypeRol.ADMIN);
            userLoginRepository.save(admin);
            System.out.println("Usuario admin creado con contraseña 'a'");
        }

    }
}


