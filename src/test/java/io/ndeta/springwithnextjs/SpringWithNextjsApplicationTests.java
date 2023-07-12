package io.ndeta.springwithnextjs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ndeta.springwithnextjs.domain.HttpResponse;
import io.ndeta.springwithnextjs.domain.Note;
import io.ndeta.springwithnextjs.enumeration.Level;
import io.ndeta.springwithnextjs.exception.NoteNotFoundException;
import io.ndeta.springwithnextjs.repo.NoteRepo;
import io.ndeta.springwithnextjs.resource.NoteResource;
import io.ndeta.springwithnextjs.service.NoteService;
import io.ndeta.springwithnextjs.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class SpringWithNextjsApplicationTests {
    @Mock
    private NoteRepo noteRepo;

    @InjectMocks
    private NoteService noteService;

//    @Test
//    void getNotes_shouldReturnAllNotes() {
//        // Arrange
//        List<Note> notes = new ArrayList<>();
//        notes.add(new Note(1L, "Title 1", "Description 1", Level.LOW, LocalDateTime.now()));
//        notes.add(new Note(2L, "Title 2", "Description 2", Level.MEDIUM, LocalDateTime.now()));
//
//        when(noteRepo.findAll()).thenReturn(notes);
//
//        // Act
//        HttpResponse<Note> response = noteService.getNotes();
//
//        // Assert
//        assertEquals(2, response.getNotes().size());
//        assertEquals("2 notes retrieved", response.getMessage());
//        assertEquals(200, response.getStatusCode());
//        assertEquals("OK", response.getStatus().toString());
//        assertNotNull(response.getTimeStamp());
//
//    }

    @Test
    void filterNotes_shouldReturnFilteredNotesByLevel() {
        // Arrange
        Level level = Level.HIGH;
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1L, "Title 1", "Description 1", Level.HIGH, LocalDateTime.now()));
        notes.add(new Note(2L, "Title 2", "Description 2", Level.HIGH, LocalDateTime.now()));

        when(noteRepo.findByLevel(level)).thenReturn(notes);

        // Act
        HttpResponse<Note> response = noteService.filterNotes(level);

        // Assert
        assertEquals(2, response.getNotes().size());
        assertEquals("2 notes are of HIGH importance", response.getMessage());
        assertEquals(200, response.getStatusCode());
        assertEquals("200 OK", response.getStatus().toString());
        assertNotNull(response.getTimeStamp());
    }

    @Test
    void saveNotes_shouldCreateANewNote() {
        // Arrange
        Note note = new Note(2L, "Title", "Description", Level.LOW, null);
        Note savedNote = new Note(1L, "Title", "Description", Level.LOW, LocalDateTime.now());

        when(noteRepo.save(note)).thenReturn(savedNote);

        // Act
        HttpResponse<Note> response = noteService.saveNotes(note);

        // Assert
        assertEquals(1, response.getNotes().size());
        assertEquals("Note created successfully", response.getMessage());
        assertEquals(201, response.getStatusCode());
        assertEquals("201 CREATED", response.getStatus().toString());
        assertNotNull(response.getTimeStamp());
    }

    @Test
    void deleteNotes_shouldDeleteNoteById() {
        // Arrange
        Long noteId = 1L;
        Note note = new Note(1L, "Title", "Description", Level.LOW, LocalDateTime.now());

        when(noteRepo.findById(noteId)).thenReturn(java.util.Optional.of(note));

        // Act
        HttpResponse<Note> response = noteService.deleteNotes(noteId);

        // Assert
        assertEquals(1, response.getNotes().size());
        assertEquals("Note deleted successfully", response.getMessage());
        assertEquals(200, response.getStatusCode());
        assertEquals("200 OK", response.getStatus().toString());
        assertNotNull(response.getTimeStamp());

        verify(noteRepo, times(1)).delete(note);
    }
    @Test
    void deleteNotes_shouldThrowNoteNotFoundException() {
        // Arrange
        Long noteId = 1L;

        when(noteRepo.findById(noteId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNotes(noteId));

        verify(noteRepo, never()).delete(any());
    }

    @Test
    void updateNotes_shouldThrowNoteNotFoundException() {
        // Arrange
        Note note = new Note(1L, "Title", "Description", Level.LOW, null);

        when(noteRepo.findById(note.getId())).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NoteNotFoundException.class, () -> noteService.updateNotes(note));

        verify(noteRepo, never()).save(any());
    }

//    @Test
//    void updateNotes_shouldUpdateExistingNote() {
//        // Arrange
//        Note note = new Note(1L, "Title", "Description", Level.LOW, LocalDateTime.now());
//
//        when(noteRepo.findById(note.getId())).thenReturn(Optional.of(note));
//        when(noteRepo.save(note)).thenReturn(note);
//
//        // Act
//        HttpResponse<Note> response = noteService.updateNotes(note);
//
//        // Assert
//        assertEquals(1, response.getNotes().size());
//        assertEquals("Note updated successfully", response.getMessage());
//        assertEquals(200, response.getStatusCode());
//        assertEquals("OK", response.getStatus().toString());
//        assertNotNull(response.getTimeStamp());
//
//        Note updatedNote = response.getNotes().iterator().next();
//        assertEquals(note.getId(), updatedNote.getId());
//        assertEquals(note.getTitle(), updatedNote.getTitle());
//        assertEquals(note.getDescription(), updatedNote.getDescription());
//        assertEquals(note.getLevel(), updatedNote.getLevel());
//
//        verify(noteRepo, times(1)).findById(note.getId());
//        verify(noteRepo, times(1)).save(note);
//    }
}
