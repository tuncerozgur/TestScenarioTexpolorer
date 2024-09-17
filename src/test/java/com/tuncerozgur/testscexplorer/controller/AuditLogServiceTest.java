package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.AuditLog;
import com.tuncerozgur.testscexplorer.repository.AuditLogRepository;
import com.tuncerozgur.testscexplorer.service.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuditLog_ExistingId_ReturnsAuditLog() {
        // Arrange
        Long id = 1L;
        AuditLog mockAuditLog = new AuditLog();
        mockAuditLog.setId(id);
        when(auditLogRepository.findById(id)).thenReturn(Optional.of(mockAuditLog));

        // Act
        AuditLog result = auditLogService.getAuditLog(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(auditLogRepository, times(1)).findById(id);
    }

    @Test
    void getAuditLog_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(auditLogRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> auditLogService.getAuditLog(id));
        verify(auditLogRepository, times(1)).findById(id);
    }

    @Test
    void createAuditLog_ValidAuditLog_ReturnsCreatedAuditLog() {
        // Arrange
        AuditLog auditLog = new AuditLog();
        when(auditLogRepository.save(auditLog)).thenReturn(auditLog);

        // Act
        AuditLog result = auditLogService.createAuditLog(auditLog);

        // Assert
        assertNotNull(result);
        verify(auditLogRepository, times(1)).save(auditLog);
    }

    @Test
    void updateAuditLog_ExistingId_ReturnsUpdatedAuditLog() {
        // Arrange
        Long id = 1L;
        AuditLog auditLog = new AuditLog();
        when(auditLogRepository.existsById(id)).thenReturn(true);
        when(auditLogRepository.save(auditLog)).thenReturn(auditLog);

        // Act
        AuditLog result = auditLogService.updateAuditLog(id, auditLog);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(auditLogRepository, times(1)).existsById(id);
        verify(auditLogRepository, times(1)).save(auditLog);
    }

    @Test
    void updateAuditLog_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        AuditLog auditLog = new AuditLog();
        when(auditLogRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> auditLogService.updateAuditLog(id, auditLog));
        verify(auditLogRepository, times(1)).existsById(id);
        verify(auditLogRepository, never()).save(any());
    }

    @Test
    void deleteAuditLog_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(auditLogRepository.existsById(id)).thenReturn(true);

        // Act
        auditLogService.deleteAuditLog(id);

        // Assert
        verify(auditLogRepository, times(1)).existsById(id);
        verify(auditLogRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAuditLog_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(auditLogRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> auditLogService.deleteAuditLog(id));
        verify(auditLogRepository, times(1)).existsById(id);
        verify(auditLogRepository, never()).deleteById(any());
    }
}