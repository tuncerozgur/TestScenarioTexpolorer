package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Suggestion;
import com.tuncerozgur.testscexplorer.repository.SuggestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuggestionServiceTest {

    @Mock
    private SuggestionRepository suggestionRepository;

    @InjectMocks
    private SuggestionService suggestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSuggestion_ExistingId_ReturnsSuggestion() {
        // Arrange
        Long id = 1L;
        Suggestion mockSuggestion = new Suggestion();
        mockSuggestion.setId(id);
        when(suggestionRepository.findById(id)).thenReturn(Optional.of(mockSuggestion));

        // Act
        Suggestion result = suggestionService.getSuggestion(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(suggestionRepository, times(1)).findById(id);
    }

    @Test
    void getSuggestion_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(suggestionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> suggestionService.getSuggestion(id));
        verify(suggestionRepository, times(1)).findById(id);
    }

    @Test
    void createSuggestion_ValidSuggestion_ReturnsCreatedSuggestion() {
        // Arrange
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("New Suggestion");
        when(suggestionRepository.save(suggestion)).thenReturn(suggestion);

        // Act
        Suggestion result = suggestionService.createSuggestion(suggestion);

        // Assert
        assertNotNull(result);
        assertEquals("New Suggestion", result.getSuggestion());
        verify(suggestionRepository, times(1)).save(suggestion);
    }

    @Test
    void updateSuggestion_ExistingId_ReturnsUpdatedSuggestion() {
        // Arrange
        Long id = 1L;
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("Updated Suggestion");
        when(suggestionRepository.existsById(id)).thenReturn(true);
        when(suggestionRepository.save(suggestion)).thenReturn(suggestion);

        // Act
        Suggestion result = suggestionService.updateSuggestion(id, suggestion);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Suggestion", result.getSuggestion());
        verify(suggestionRepository, times(1)).existsById(id);
        verify(suggestionRepository, times(1)).save(suggestion);
    }

    @Test
    void updateSuggestion_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Suggestion suggestion = new Suggestion();
        when(suggestionRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> suggestionService.updateSuggestion(id, suggestion));
        verify(suggestionRepository, times(1)).existsById(id);
        verify(suggestionRepository, never()).save(any());
    }

    @Test
    void deleteSuggestion_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(suggestionRepository.existsById(id)).thenReturn(true);

        // Act
        suggestionService.deleteSuggestion(id);

        // Assert
        verify(suggestionRepository, times(1)).existsById(id);
        verify(suggestionRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSuggestion_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(suggestionRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> suggestionService.deleteSuggestion(id));
        verify(suggestionRepository, times(1)).existsById(id);
        verify(suggestionRepository, never()).deleteById(any());
    }
}
