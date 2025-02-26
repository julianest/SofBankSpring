package com.jhsoft.sofbank.controllers;

import com.jhsoft.sofbank.domains.entities.UserLogin;
import com.jhsoft.sofbank.domains.repositories.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/register")
public class UserLoginController {
    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody List<UserLogin> userLogins) {
        for (UserLogin userLogin : userLogins) {
            userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
            userLogin.setEnabled(true);
            userLoginRepository.save(userLogin);
        }
        return ResponseEntity.ok("Usuarios registrados exitosamente");
    }

/*    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserLogin userLogin) {
        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        userLogin.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        for (Role role : userLogin.getRoles()) {
            Role existingRole = roleRepository.findByTypeRol(role.getTypeRol())
                    .orElseThrow(() -> new IllegalArgumentException("Role no encontrado: " + role.getTypeRol()));
            roles.add(existingRole);
        }
        userLogin.setRoles(roles);

        userLoginRepository.save(userLogin);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }*/
}
