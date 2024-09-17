package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Feedback;
import com.tuncerozgur.testscexplorer.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFeedback_ExistingId_ReturnsFeedback() {
        // Arrange
        Long id = 1L;
        Feedback mockFeedback = new Feedback();
        mockFeedback.setId(id);
        when(feedbackRepository.findById(id)).thenReturn(Optional.of(mockFeedback));

        // Act
        Feedback result = feedbackService.getFeedback(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(feedbackRepository, times(1)).findById(id);
    }

    @Test
    void getFeedback_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(feedbackRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> feedbackService.getFeedback(id));
        verify(feedbackRepository, times(1)).findById(id);
    }

    @Test
    void createFeedback_ValidFeedback_ReturnsCreatedFeedback() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setFeedbackText("Great service!");
        feedback.setRating(5);
        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // Act
        Feedback result = feedbackService.createFeedback(feedback);

        // Assert
        assertNotNull(result);
        assertEquals("Great service!", result.getFeedbackText());
        assertEquals(5, result.getRating());
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void updateFeedback_ExistingId_ReturnsUpdatedFeedback() {
        // Arrange
        Long id = 1L;
        Feedback feedback = new Feedback();
        feedback.setFeedbackText("Updated feedback");
        feedback.setRating(4);
        when(feedbackRepository.existsById(id)).thenReturn(true);
        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // Act
        Feedback result = feedbackService.updateFeedback(id, feedback);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated feedback", result.getFeedbackText());
        assertEquals(4, result.getRating());
        verify(feedbackRepository, times(1)).existsById(id);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void updateFeedback_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Feedback feedback = new Feedback();
        when(feedbackRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> feedbackService.updateFeedback(id, feedback));
        verify(feedbackRepository, times(1)).existsById(id);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void deleteFeedback_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(feedbackRepository.existsById(id)).thenReturn(true);

        // Act
        feedbackService.deleteFeedback(id);

        // Assert
        verify(feedbackRepository, times(1)).existsById(id);
        verify(feedbackRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteFeedback_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(feedbackRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> feedbackService.deleteFeedback(id));
        verify(feedbackRepository, times(1)).existsById(id);
        verify(feedbackRepository, never()).deleteById(any());
    }
}
