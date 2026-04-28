package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.user.ChangePasswordRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.user.UpdateMyAccountRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.Role;
import com.school.ppmg.computer_equipment_store_system_api.models.User;
import com.school.ppmg.computer_equipment_store_system_api.repositories.UserRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user@example.com", null)
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void updateMyAccount_shouldUpdateFields() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .passwordHash("encoded")
                .firstName("Old")
                .lastName("Name")
                .phone("+359888111111")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateMyAccountRequest request = new UpdateMyAccountRequest(
                "Ivan",
                "Petrov",
                "+359888123456"
        );

        var response = userService.updateMyAccount(request);

        assertEquals("Ivan", response.firstName());
        assertEquals("Petrov", response.lastName());
        assertEquals("+359888123456", response.phone());
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_shouldThrow_whenCurrentPasswordIsWrong() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .passwordHash("encoded-old")
                .firstName("Ivan")
                .lastName("Petrov")
                .phone("+359888123456")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-current", "encoded-old")).thenReturn(false);

        ChangePasswordRequest request = new ChangePasswordRequest(
                "wrong-current",
                "NewPassword1!",
                "NewPassword1!"
        );

        assertThrows(ResponseStatusException.class, () -> userService.changePassword(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void changePassword_shouldSaveNewPassword() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .passwordHash("encoded-old")
                .firstName("Ivan")
                .lastName("Petrov")
                .phone("+359888123456")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("OldPassword1!", "encoded-old")).thenReturn(true);
        when(passwordEncoder.matches("NewPassword1!", "encoded-old")).thenReturn(false);
        when(passwordEncoder.encode("NewPassword1!")).thenReturn("encoded-new");

        ChangePasswordRequest request = new ChangePasswordRequest(
                "OldPassword1!",
                "NewPassword1!",
                "NewPassword1!"
        );

        userService.changePassword(request);

        assertEquals("encoded-new", user.getPasswordHash());
        verify(userRepository).save(user);
    }
}