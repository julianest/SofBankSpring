package com.jhsoft.SofBank.config;

import com.jhsoft.SofBank.domains.entities.UserLogin;
import com.jhsoft.SofBank.domains.repositories.UserLoginRepository;
import com.jhsoft.SofBank.domains.services.TypeRol;
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

/*
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    private final PasswordEncoder passwordEncoder;
    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByTypeRol(TypeRol.ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, TypeRol.ADMIN, "Administrador del sistema"));
        }
        if (roleRepository.findByTypeRol(TypeRol.CASHER).isEmpty()) {
            roleRepository.save(new Role(null, TypeRol.CASHER, "Usuario estándar"));
        }

        if (userLoginRepository.findByUsername("admin").isEmpty()) {
            String password = "a";  // Contraseña para el usuario admin
            String encodedPassword = passwordEncoder.encode(password);
            System.out.println("Contraseña cifrada: " + encodedPassword);  // Esto imprime la contraseña cifrada en consola

            // Crear el usuario con el rol de ADMIN
            UserLogin adminUser = new UserLogin();
            adminUser.setUsername("admin");
            adminUser.setPassword(encodedPassword);
            adminUser.setEnabled(true);

            // Asignar el rol de ADMIN al usuario
            Role adminRole = roleRepository.findByTypeRol(TypeRol.ADMIN)
                    .orElseThrow(() -> new IllegalStateException("Role ADMIN no encontrado"));
            adminUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
            adminRole = roleRepository.saveAndFlush(adminRole);
            adminUser.getRoles().add(adminRole);

            // Guardar el usuario admin
            userLoginRepository.save(adminUser);
        }

    }
}
 */
