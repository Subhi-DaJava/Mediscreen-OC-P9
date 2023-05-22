package com.oc.practitionernotes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.practitionernotes.exception.NoteNotFoundException;
import com.oc.practitionernotes.model.Note;
import com.oc.practitionernotes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private NoteService noteService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());
        String noteId1 = UUID.randomUUID().toString();
        note1.setId(noteId1);
        String noteId2 = UUID.randomUUID().toString();
        note2.setId(noteId2);
        expectedNotes = List.of(note1, note2);
        when(noteService.getAllNotes()).thenReturn(expectedNotes);
        // When
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].patLastName", is("Jean")))
                .andReturn();
        // Then
        verify(noteService).getAllNotes();
    }

    @Test
    void testGetAllNotesOfPatientByPatId() throws Exception {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(1L, "Jean", "Comment 2", LocalDate.now());
        String noteId1 = UUID.randomUUID().toString();
        note1.setId(noteId1);
        String noteId2 = UUID.randomUUID().toString();
        note2.setId(noteId2);
        expectedNotes = List.of(note1, note2);
        when(noteService.getPatientAllNotesByPatientId(anyLong())).thenReturn(expectedNotes);

        // When
        mockMvc.perform(get("/api/notes/by-patId/{patId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].patLastName", is("Jean")))
                .andExpect(jsonPath("$.length()", is(2)))
                .andReturn();

        verify(noteService).getPatientAllNotesByPatientId(anyLong());
    }

    @Test
    void testGetAllNotesOfPatientPatLastName() throws Exception {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(1L, "Jean", "Comment 2", LocalDate.now());
        String noteId1 = UUID.randomUUID().toString();
        note1.setId(noteId1);
        String noteId2 = UUID.randomUUID().toString();
        note2.setId(noteId2);
        expectedNotes = List.of(note1, note2);
        when(noteService.getPatientAllNotesByPatientLastName(anyString())).thenReturn(expectedNotes);

        // When
        mockMvc.perform(get("/api/notes/by-lastName/{lastName}", "Jean"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patId", is(1)))
                .andExpect(jsonPath("$.length()", is(2)))
                .andReturn();

        verify(noteService).getPatientAllNotesByPatientLastName(anyString());
    }

    @Test
    void testAddNote() throws Exception {
        // Given
        Note newNoteToSaved = new Note(20L, "Jean", "Comment 1", LocalDate.now());
        String noteId = UUID.randomUUID().toString();
        newNoteToSaved.setId(noteId);
        when(noteService.saveNote(newNoteToSaved)).thenReturn(newNoteToSaved);

        // When
        mockMvc.perform(post("/api/notes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNoteToSaved)))
                .andExpect(status().isCreated())
                .andReturn();
        // Then
        verify(noteService).saveNote(any(Note.class));
    }

    @Test
    void testUpdateById() throws Exception {
        // Given
        String noteId = UUID.randomUUID().toString();
        Note updatedNote = new Note(20L, "Jean", "Comment before", LocalDate.now());
        updatedNote.setId(noteId);
        Note noteUpdated = new Note(20L, "Jean", "Comment updated", LocalDate.now());
        noteUpdated.setId(noteId);
        when(noteService.updateNoteById(noteId, updatedNote)).thenReturn(noteUpdated);

        // When
        mockMvc.perform(put("/api/notes/{id}", noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNote)))
                .andExpect(status().isOk())
                .andReturn();
        // Then
        verify(noteService).updateNoteById(anyString(), any(Note.class));
    }

    @Test
    void testDeleteById() throws Exception {
        // Given
        String noteId = UUID.randomUUID().toString();
        Note existingNote = new Note(20L, "Jean", "Comment before", LocalDate.now());
        existingNote.setId(noteId);
        when(noteService.getNoteById(anyString())).thenReturn(existingNote);
        doNothing().when(noteService).deleteById(noteId);

        // When
        mockMvc.perform(delete("/api/notes/{id}", noteId))
                .andExpect(status().isOk());

        // Then
        verify(noteService).deleteById(anyString());

    }

    @Test
    void testGetNoteById() throws Exception {
        // Given
        String noteId = UUID.randomUUID().toString();
        Note existingNote = new Note(20L, "Jean", "Comment before", LocalDate.now());
        existingNote.setId(noteId);
        when(noteService.getNoteById(anyString())).thenReturn(existingNote);

        // when
        mockMvc.perform(get("/api/notes/{id}", noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patId", is(20)));
    }

    @Test
    void testGetNoteByIdShouldThrowNoteNotFoundException() throws Exception {
        // Given
        String noteId = UUID.randomUUID().toString();

        when(noteService.getNoteById(anyString())).thenThrow(new NoteNotFoundException("Note with id:{%s} doesn't exist in DB!".formatted(noteId)));

        // when
        mockMvc.perform(get("/api/notes/{id}", noteId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Note with id:{%s} doesn't exist in DB!".formatted(noteId)))
                .andExpect(jsonPath("$.description").value("uri=/api/notes/%s".formatted(noteId)));
    }

    @Test
    void testAddNoteToPatientByPatId() throws Exception {
        // Given
        String noteId = UUID.randomUUID().toString();
        Note existingNote = new Note(20L, "Jean", "Comment before", LocalDate.now());
        existingNote.setId(noteId);

        String newComment = "Smoker, Ill";
        Note noteSaved = new Note(20L, "Jean", newComment, LocalDate.now());
        existingNote.setId(noteId);

        when(noteService.addNoteByPatId(20L, newComment)).thenReturn(noteSaved);

        // When
        mockMvc.perform(post("/api/notes/{patId}", 20L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("note",newComment))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        verify(noteService).addNoteByPatId(anyLong(), anyString());

    }
}