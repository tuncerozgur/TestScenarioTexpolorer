package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.User;
import com.tuncerozgur.testscexplorer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser_ExistingId_ReturnsUser() {
        // Arrange
        Long id = 1L;
        User mockUser = new User();
        mockUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUser(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getUser_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUser(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void createUser_ValidUser_ReturnsCreatedUser() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ExistingId_ReturnsUpdatedUser() {
        // Arrange
        Long id = 1L;
        User user = new User();
        user.setUsername("updateduser");
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(id, user);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("updateduser", result.getUsername());
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        User user = new User();
        when(userRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(id, user));
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUser_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(id));
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).deleteById(any());
    }
}
