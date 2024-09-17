package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Subscription;
import com.tuncerozgur.testscexplorer.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSubscription_ExistingId_ReturnsSubscription() {
        // Arrange
        Long id = 1L;
        Subscription mockSubscription = new Subscription();
        mockSubscription.setId(id);
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(mockSubscription));

        // Act
        Subscription result = subscriptionService.getSubscription(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(subscriptionRepository, times(1)).findById(id);
    }

    @Test
    void getSubscription_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(subscriptionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subscriptionService.getSubscription(id));
        verify(subscriptionRepository, times(1)).findById(id);
    }

    @Test
    void createSubscription_ValidSubscription_ReturnsCreatedSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setPlanName("Basic Plan");
        subscription.setPrice(29.99);
        subscription.setDuration("monthly");
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Act
        Subscription result = subscriptionService.createSubscription(subscription);

        // Assert
        assertNotNull(result);
        assertEquals("Basic Plan", result.getPlanName());
        assertEquals(29.99, result.getPrice());
        assertEquals("monthly", result.getDuration());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void updateSubscription_ExistingId_ReturnsUpdatedSubscription() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        subscription.setPlanName("Updated Plan");
        subscription.setPrice(49.99);
        subscription.setDuration("yearly");
        when(subscriptionRepository.existsById(id)).thenReturn(true);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Act
        Subscription result = subscriptionService.updateSubscription(id, subscription);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Plan", result.getPlanName());
        assertEquals(49.99, result.getPrice());
        assertEquals("yearly", result.getDuration());
        verify(subscriptionRepository, times(1)).existsById(id);
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void updateSubscription_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Subscription subscription = new Subscription();
        when(subscriptionRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subscriptionService.updateSubscription(id, subscription));
        verify(subscriptionRepository, times(1)).existsById(id);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void deleteSubscription_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(subscriptionRepository.existsById(id)).thenReturn(true);

        // Act
        subscriptionService.deleteSubscription(id);

        // Assert
        verify(subscriptionRepository, times(1)).existsById(id);
        verify(subscriptionRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSubscription_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(subscriptionRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subscriptionService.deleteSubscription(id));
        verify(subscriptionRepository, times(1)).existsById(id);
        verify(subscriptionRepository, never()).deleteById(any());
    }
}
