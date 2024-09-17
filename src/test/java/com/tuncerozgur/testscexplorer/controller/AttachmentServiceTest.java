package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Attachment;
import com.tuncerozgur.testscexplorer.repository.AttachmentRepository;
import com.tuncerozgur.testscexplorer.service.AttachmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttachmentServiceTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private AttachmentService attachmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAttachment_ExistingId_ReturnsAttachment() {
        // Arrange
        Long id = 1L;
        Attachment mockAttachment = new Attachment();
        mockAttachment.setId(id);
        when(attachmentRepository.findById(id)).thenReturn(Optional.of(mockAttachment));

        // Act
        Attachment result = attachmentService.getAttachment(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(attachmentRepository, times(1)).findById(id);
    }

    @Test
    void getAttachment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(attachmentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> attachmentService.getAttachment(id));
        verify(attachmentRepository, times(1)).findById(id);
    }

    @Test
    void createAttachment_ValidAttachment_ReturnsCreatedAttachment() {
        // Arrange
        Attachment attachment = new Attachment();
        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        // Act
        Attachment result = attachmentService.createAttachment(attachment);

        // Assert
        assertNotNull(result);
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    void updateAttachment_ExistingId_ReturnsUpdatedAttachment() {
        // Arrange
        Long id = 1L;
        Attachment attachment = new Attachment();
        when(attachmentRepository.existsById(id)).thenReturn(true);
        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        // Act
        Attachment result = attachmentService.updateAttachment(id, attachment);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(attachmentRepository, times(1)).existsById(id);
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    void updateAttachment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Attachment attachment = new Attachment();
        when(attachmentRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> attachmentService.updateAttachment(id, attachment));
        verify(attachmentRepository, times(1)).existsById(id);
        verify(attachmentRepository, never()).save(any());
    }

    @Test
    void deleteAttachment_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(attachmentRepository.existsById(id)).thenReturn(true);

        // Act
        attachmentService.deleteAttachment(id);

        // Assert
        verify(attachmentRepository, times(1)).existsById(id);
        verify(attachmentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAttachment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(attachmentRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> attachmentService.deleteAttachment(id));
        verify(attachmentRepository, times(1)).existsById(id);
        verify(attachmentRepository, never()).deleteById(any());
    }
}
