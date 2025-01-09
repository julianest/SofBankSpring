package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.entities.UserLogin;
import com.jhsoft.SofBank.domains.repositories.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserLoginServiceTest {
    @Mock
    private UserLoginRepository userLoginRepository;

    @InjectMocks
    private UserLoginService userLoginService;

    private UserLogin userLogin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userLogin = new UserLogin();
        userLogin.setIdUser(1L);
        userLogin.setUsername("testuser");
        userLogin.setPassword("password");
        userLogin.setEnabled(true);
        userLogin.setTypeRol(TypeRol.ADMIN);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Configurar el comportamiento del repositorio para devolver el usuario
        when(userLoginRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(userLogin));

        // Llamar al método loadUserByUsername
        UserDetails userDetails = userLoginService.loadUserByUsername("testuser");

        // Verificar el comportamiento
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertEquals("ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void testLoadUserByUsername_UsernameNotFound() {
        // Configurar el comportamiento del repositorio para que no devuelva ningún usuario
        when(userLoginRepository.findByUsername("testuser")).thenReturn(java.util.Optional.empty());

        // Llamar al método loadUserByUsername y verificar que lanza la excepción
        assertThrows(UsernameNotFoundException.class, () -> userLoginService.loadUserByUsername("testuser"));
    }

    @Test
    public void testLoadUserByUsername_DisabledUser() {
        // Cambiar el estado del usuario a deshabilitado
        userLogin.setEnabled(false);

        // Configurar el comportamiento del repositorio
        when(userLoginRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(userLogin));

        // Llamar al método loadUserByUsername
        UserDetails userDetails = userLoginService.loadUserByUsername("testuser");

        // Verificar que el usuario está deshabilitado
        assertNotNull(userDetails);
        assertFalse(userDetails.isEnabled());
    }
}
