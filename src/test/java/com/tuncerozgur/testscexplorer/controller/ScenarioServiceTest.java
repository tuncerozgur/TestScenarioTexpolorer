package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Scenario;
import com.tuncerozgur.testscexplorer.repository.ScenarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScenarioServiceTest {

    @Mock
    private ScenarioRepository scenarioRepository;

    @InjectMocks
    private ScenarioService scenarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getScenario_ExistingId_ReturnsScenario() {
        // Arrange
        Long id = 1L;
        Scenario mockScenario = new Scenario();
        mockScenario.setId(id);
        when(scenarioRepository.findById(id)).thenReturn(Optional.of(mockScenario));

        // Act
        Scenario result = scenarioService.getScenario(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(scenarioRepository, times(1)).findById(id);
    }

    @Test
    void getScenario_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(scenarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scenarioService.getScenario(id));
        verify(scenarioRepository, times(1)).findById(id);
    }

    @Test
    void createScenario_ValidScenario_ReturnsCreatedScenario() {
        // Arrange
        Scenario scenario = new Scenario();
        scenario.setName("Scenario 1");
        scenario.setDescription("Description");
        when(scenarioRepository.save(scenario)).thenReturn(scenario);

        // Act
        Scenario result = scenarioService.createScenario(scenario);

        // Assert
        assertNotNull(result);
        assertEquals("Scenario 1", result.getName());
        assertEquals("Description", result.getDescription());
        verify(scenarioRepository, times(1)).save(scenario);
    }

    @Test
    void updateScenario_ExistingId_ReturnsUpdatedScenario() {
        // Arrange
        Long id = 1L;
        Scenario scenario = new Scenario();
        scenario.setName("Updated Scenario");
        scenario.setDescription("Updated Description");
        when(scenarioRepository.existsById(id)).thenReturn(true);
        when(scenarioRepository.save(scenario)).thenReturn(scenario);

        // Act
        Scenario result = scenarioService.updateScenario(id, scenario);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Scenario", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(scenarioRepository, times(1)).existsById(id);
        verify(scenarioRepository, times(1)).save(scenario);
    }

    @Test
    void updateScenario_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Scenario scenario = new Scenario();
        when(scenarioRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scenarioService.updateScenario(id, scenario));
        verify(scenarioRepository, times(1)).existsById(id);
        verify(scenarioRepository, never()).save(any());
    }

    @Test
    void deleteScenario_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(scenarioRepository.existsById(id)).thenReturn(true);

        // Act
        scenarioService.deleteScenario(id);

        // Assert
        verify(scenarioRepository, times(1)).existsById(id);
        verify(scenarioRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteScenario_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(scenarioRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scenarioService.deleteScenario(id));
        verify(scenarioRepository, times(1)).existsById(id);
        verify(scenarioRepository, never()).deleteById(any());
    }
}
