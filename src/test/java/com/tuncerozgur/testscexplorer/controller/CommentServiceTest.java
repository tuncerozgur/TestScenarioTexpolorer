package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Comment;
import com.tuncerozgur.testscexplorer.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getComment_ExistingId_ReturnsComment() {
        // Arrange
        Long id = 1L;
        Comment mockComment = new Comment();
        mockComment.setId(id);
        when(commentRepository.findById(id)).thenReturn(Optional.of(mockComment));

        // Act
        Comment result = commentService.getComment(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(commentRepository, times(1)).findById(id);
    }

    @Test
    void getComment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.getComment(id));
        verify(commentRepository, times(1)).findById(id);
    }

    @Test
    void createComment_ValidComment_ReturnsCreatedComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setContent("Test comment");
        when(commentRepository.save(comment)).thenReturn(comment);

        // Act
        Comment result = commentService.createComment(comment);

        // Assert
        assertNotNull(result);
        assertEquals("Test comment", result.getContent());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void updateComment_ExistingId_ReturnsUpdatedComment() {
        // Arrange
        Long id = 1L;
        Comment comment = new Comment();
        comment.setContent("Updated comment");
        when(commentRepository.existsById(id)).thenReturn(true);
        when(commentRepository.save(comment)).thenReturn(comment);

        // Act
        Comment result = commentService.updateComment(id, comment);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated comment", result.getContent());
        verify(commentRepository, times(1)).existsById(id);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void updateComment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Comment comment = new Comment();
        when(commentRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.updateComment(id, comment));
        verify(commentRepository, times(1)).existsById(id);
        verify(commentRepository, never()).save(any());
    }

    @Test
    void deleteComment_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(commentRepository.existsById(id)).thenReturn(true);

        // Act
        commentService.deleteComment(id);

        // Assert
        verify(commentRepository, times(1)).existsById(id);
        verify(commentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteComment_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(commentRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.deleteComment(id));
        verify(commentRepository, times(1)).existsById(id);
        verify(commentRepository, never()).deleteById(any());
    }
}