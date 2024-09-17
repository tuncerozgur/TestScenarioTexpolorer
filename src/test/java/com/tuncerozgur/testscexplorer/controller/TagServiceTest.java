package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Tag;
import com.tuncerozgur.testscexplorer.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTag_ExistingId_ReturnsTag() {
        // Arrange
        Long id = 1L;
        Tag mockTag = new Tag();
        mockTag.setId(id);
        when(tagRepository.findById(id)).thenReturn(Optional.of(mockTag));

        // Act
        Tag result = tagService.getTag(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void getTag_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tagService.getTag(id));
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void createTag_ValidTag_ReturnsCreatedTag() {
        // Arrange
        Tag tag = new Tag();
        tag.setName("New Tag");
        when(tagRepository.save(tag)).thenReturn(tag);

        // Act
        Tag result = tagService.createTag(tag);

        // Assert
        assertNotNull(result);
        assertEquals("New Tag", result.getName());
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void updateTag_ExistingId_ReturnsUpdatedTag() {
        // Arrange
        Long id = 1L;
        Tag tag = new Tag();
        tag.setName("Updated Tag");
        when(tagRepository.existsById(id)).thenReturn(true);
        when(tagRepository.save(tag)).thenReturn(tag);

        // Act
        Tag result = tagService.updateTag(id, tag);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Tag", result.getName());
        verify(tagRepository, times(1)).existsById(id);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void updateTag_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        Tag tag = new Tag();
        when(tagRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tagService.updateTag(id, tag));
        verify(tagRepository, times(1)).existsById(id);
        verify(tagRepository, never()).save(any());
    }

    @Test
    void deleteTag_ExistingId_DeletesSuccessfully() {
        // Arrange
        Long id = 1L;
        when(tagRepository.existsById(id)).thenReturn(true);

        // Act
        tagService.deleteTag(id);

        // Assert
        verify(tagRepository, times(1)).existsById(id);
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteTag_NonExistingId_ThrowsRuntimeException() {
        // Arrange
        Long id = 1L;
        when(tagRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tagService.deleteTag(id));
        verify(tagRepository, times(1)).existsById(id);
        verify(tagRepository, never()).deleteById(any());
    }
}
