package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Role;
import com.tuncerozgur.testscexplorer.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRole_ExistingId_ReturnsRole() {
        // Arrange
        Long id = 1L;
        Role mockRole = new Role();
        mockRole.setId(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(mockRole));

        // Act
        Role result = roleService.getRole(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(roleRepository, times(1)).findById(id);
    }

    @Test
    void getRole_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.getRole(id));
        verify(roleRepository, times(1)).findById(id);
    }

    @Test
    void createRole_ValidRole_ReturnsCreatedRole() {
        // Arrange
        Role role = new Role();
        role.setName("Admin");
        when(roleRepository.save(role)).thenReturn(role);

        // Act
        Role result = roleService.createRole(role);

        // Assert
        assertNotNull(result);
        assertEquals("Admin", result.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_ExistingId_ReturnsUpdatedRole() {
        // Arrange
        Long id = 1L;
        Role role = new Role();
        role.setName("Updated Role");
        when(roleRepository.existsById(id)).thenReturn(true);
        when(roleRepository.save(role)).thenReturn(role);

        // Act
        Role result = roleService.updateRole(id, role);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Role", result.getName());
        verify(roleRepository, times(1)).existsById(id);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Role role = new Role();
        when(roleRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.updateRole(id, role));
        verify(roleRepository, times(1)).existsById(id);
        verify(roleRepository, never()).save(any());
    }

    @Test
    void deleteRole_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(roleRepository.existsById(id)).thenReturn(true);

        // Act
        roleService.deleteRole(id);

        // Assert
        verify(roleRepository, times(1)).existsById(id);
        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteRole_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(roleRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.deleteRole(id));
        verify(roleRepository, times(1)).existsById(id);
        verify(roleRepository, never()).deleteById(any());
    }
}
