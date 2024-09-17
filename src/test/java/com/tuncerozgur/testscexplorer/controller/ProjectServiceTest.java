package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Project;
import com.tuncerozgur.testscexplorer.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProject_ExistingId_ReturnsProject() {
        // Arrange
        Long id = 1L;
        Project mockProject = new Project();
        mockProject.setId(id);
        when(projectRepository.findById(id)).thenReturn(Optional.of(mockProject));

        // Act
        Project result = projectService.getProject(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(projectRepository, times(1)).findById(id);
    }

    @Test
    void getProject_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.getProject(id));
        verify(projectRepository, times(1)).findById(id);
    }

    @Test
    void createProject_ValidProject_ReturnsCreatedProject() {
        // Arrange
        Project project = new Project();
        project.setName("New Project");
        project.setDescription("Description");
        when(projectRepository.save(project)).thenReturn(project);

        // Act
        Project result = projectService.createProject(project);

        // Assert
        assertNotNull(result);
        assertEquals("New Project", result.getName());
        assertEquals("Description", result.getDescription());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void updateProject_ExistingId_ReturnsUpdatedProject() {
        // Arrange
        Long id = 1L;
        Project project = new Project();
        project.setName("Updated Project");
        project.setDescription("Updated Description");
        when(projectRepository.existsById(id)).thenReturn(true);
        when(projectRepository.save(project)).thenReturn(project);

        // Act
        Project result = projectService.updateProject(id, project);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Project", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(projectRepository, times(1)).existsById(id);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void updateProject_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Project project = new Project();
        when(projectRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.updateProject(id, project));
        verify(projectRepository, times(1)).existsById(id);
        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteProject_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(projectRepository.existsById(id)).thenReturn(true);

        // Act
        projectService.deleteProject(id);

        // Assert
        verify(projectRepository, times(1)).existsById(id);
        verify(projectRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProject_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(projectRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.deleteProject(id));
        verify(projectRepository, times(1)).existsById(id);
        verify(projectRepository, never()).deleteById(any());
    }
}
