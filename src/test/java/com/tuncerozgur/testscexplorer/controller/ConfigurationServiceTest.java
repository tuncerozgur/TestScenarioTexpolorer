package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Configuration;
import com.tuncerozgur.testscexplorer.repository.ConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationService configurationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getConfiguration_ExistingId_ReturnsConfiguration() {
        // Arrange
        Long id = 1L;
        Configuration mockConfiguration = new Configuration();
        mockConfiguration.setId(id);
        when(configurationRepository.findById(id)).thenReturn(Optional.of(mockConfiguration));

        // Act
        Configuration result = configurationService.getConfiguration(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(configurationRepository, times(1)).findById(id);
    }

    @Test
    void getConfiguration_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(configurationRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> configurationService.getConfiguration(id));
        verify(configurationRepository, times(1)).findById(id);
    }

    @Test
    void createConfiguration_ValidConfiguration_ReturnsCreatedConfiguration() {
        // Arrange
        Configuration configuration = new Configuration();
        configuration.setKey("key1");
        configuration.setValue("value1");
        when(configurationRepository.save(configuration)).thenReturn(configuration);

        // Act
        Configuration result = configurationService.createConfiguration(configuration);

        // Assert
        assertNotNull(result);
        assertEquals("key1", result.getKey());
        assertEquals("value1", result.getValue());
        verify(configurationRepository, times(1)).save(configuration);
    }

    @Test
    void updateConfiguration_ExistingId_ReturnsUpdatedConfiguration() {
        // Arrange
        Long id = 1L;
        Configuration configuration = new Configuration();
        configuration.setKey("updatedKey");
        configuration.setValue("updatedValue");
        when(configurationRepository.existsById(id)).thenReturn(true);
        when(configurationRepository.save(configuration)).thenReturn(configuration);

        // Act
        Configuration result = configurationService.updateConfiguration(id, configuration);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("updatedKey", result.getKey());
        assertEquals("updatedValue", result.getValue());
        verify(configurationRepository, times(1)).existsById(id);
        verify(configurationRepository, times(1)).save(configuration);
    }

    @Test
    void updateConfiguration_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Configuration configuration = new Configuration();
        when(configurationRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> configurationService.updateConfiguration(id, configuration));
        verify(configurationRepository, times(1)).existsById(id);
        verify(configurationRepository, never()).save(any());
    }

    @Test
    void deleteConfiguration_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(configurationRepository.existsById(id)).thenReturn(true);

        // Act
        configurationService.deleteConfiguration(id);

        // Assert
        verify(configurationRepository, times(1)).existsById(id);
        verify(configurationRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteConfiguration_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(configurationRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> configurationService.deleteConfiguration(id));
        verify(configurationRepository, times(1)).existsById(id);
        verify(configurationRepository, never()).deleteById(any());
    }
}
