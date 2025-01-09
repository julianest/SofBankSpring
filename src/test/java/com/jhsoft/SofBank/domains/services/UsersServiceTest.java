package com.jhsoft.SofBank.domains.services;

import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.repositories.UsersRepository;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsersServiceTest {

    @InjectMocks
    private UsersServices usersService;

    @Mock
    private UsersRepository usersRepository;

    private UsersDTO usersDTO;

    @BeforeEach
    public void setUp() {
        usersDTO = new UsersDTO("1234", "John", "Doe", "john.doe@example.com");
    }

    // Prueba 1: Test para crear un usuario
    @Test
    public void testCreateUser() {

        Users savedUser = new Users(usersDTO.getIdentification(), usersDTO.getName(), usersDTO.getLastName(), usersDTO.getEmail());
        savedUser.setCreatedBy("UserSystem");

        when(usersRepository.save(any(Users.class))).thenReturn(savedUser);

        Users result = usersService.createUser(usersDTO);

        assertNotNull(result);
        assertEquals("1234", result.getIdentification());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    // Prueba 2: Test para obtener un usuario por identificación
    @Test
    public void testGetUserByIdentification_Success() {
        Users existingUser = new Users("1234", "John", "Doe", "john.doe@example.com");

        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.of(existingUser));

        Users result = usersService.getUserByIdentification("1234");

        assertNotNull(result);
        assertEquals("1234", result.getIdentification());
    }


    @Test
    public void testGetUserByIdentification_NotFound() {
        when(usersRepository.findByIdentification("12345")).thenReturn(Optional.empty());
        assertThrows(UsersNotFoundException.class, () -> usersService.getUserByIdentification("12345"));
    }

    // Prueba 3: Test para eliminar un usuario
    @Test
    public void testDeleteUser_Success() {
        Users existingUser = new Users("1234", "John", "Doe", "john.doe@example.com");

        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.of(existingUser));

        usersService.deleteUser("1234");

        verify(usersRepository, times(1)).delete(existingUser);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.empty());

        assertThrows(UsersNotFoundException.class, () -> usersService.deleteUser("1234"));
    }

    // Prueba 4: Test para actualizar un usuario
    @Test
    public void testUpdateUser_Success() {
        Users existingUser = new Users("1234", "John", "Doe", "john.doe@example.com");
        UsersDTO updatedUserDTO = new UsersDTO("1234", "John", "Smith", "john.smith@example.com");

        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.of(existingUser));
        when(usersRepository.save(any(Users.class))).thenReturn(existingUser);

        Users result = usersService.updateUser("1234", updatedUserDTO);

        assertNotNull(result);
        assertEquals("1234", result.getIdentification());
        assertEquals("John", result.getName());
        assertEquals("Smith", result.getLastName());
        assertEquals("john.smith@example.com", result.getEmail());

        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testUpdateUser_NotFound() {
        UsersDTO updatedUserDTO = new UsersDTO("1234", "John", "Smith", "john.smith@example.com");

        when(usersRepository.findByIdentification("1234")).thenReturn(Optional.empty());

        assertThrows(UsersNotFoundException.class, () -> usersService.updateUser("1234", updatedUserDTO));
    }
}
