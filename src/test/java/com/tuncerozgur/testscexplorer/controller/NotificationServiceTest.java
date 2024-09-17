package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Notification;
import com.tuncerozgur.testscexplorer.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNotification_ExistingId_ReturnsNotification() {
        // Arrange
        Long id = 1L;
        Notification mockNotification = new Notification();
        mockNotification.setId(id);
        when(notificationRepository.findById(id)).thenReturn(Optional.of(mockNotification));

        // Act
        Notification result = notificationService.getNotification(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(notificationRepository, times(1)).findById(id);
    }

    @Test
    void getNotification_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> notificationService.getNotification(id));
        verify(notificationRepository, times(1)).findById(id);
    }

    @Test
    void createNotification_ValidNotification_ReturnsCreatedNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("New notification");
        notification.setRead(false);
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Act
        Notification result = notificationService.createNotification(notification);

        // Assert
        assertNotNull(result);
        assertEquals("New notification", result.getMessage());
        assertFalse(result.isRead());
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void updateNotification_ExistingId_ReturnsUpdatedNotification() {
        // Arrange
        Long id = 1L;
        Notification notification = new Notification();
        notification.setMessage("Updated message");
        notification.setRead(true);
        when(notificationRepository.existsById(id)).thenReturn(true);
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Act
        Notification result = notificationService.updateNotification(id, notification);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated message", result.getMessage());
        assertTrue(result.isRead());
        verify(notificationRepository, times(1)).existsById(id);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void updateNotification_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Notification notification = new Notification();
        when(notificationRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> notificationService.updateNotification(id, notification));
        verify(notificationRepository, times(1)).existsById(id);
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void deleteNotification_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(notificationRepository.existsById(id)).thenReturn(true);

        // Act
        notificationService.deleteNotification(id);

        // Assert
        verify(notificationRepository, times(1)).existsById(id);
        verify(notificationRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteNotification_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(notificationRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> notificationService.deleteNotification(id));
        verify(notificationRepository, times(1)).existsById(id);
        verify(notificationRepository, never()).deleteById(any());
    }
}
