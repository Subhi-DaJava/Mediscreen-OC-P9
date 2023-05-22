package com.oc.practitionernotes.integration_test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.practitionernotes.model.Note;
import com.oc.practitionernotes.test_repository.NoteTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NoteControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteTestRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }
    @Test
    void testGetAllNotes() throws Exception {
        // Given
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());

        repository.insert(List.of(note1, note2));
        // When
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].patLastName", is("Jean")))
                .andReturn();
    }

    @Test
    void testGetAllNotesOfPatientPatId() throws Exception {
        // Given
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());

        repository.insert(List.of(note1, note2));

        // When
        mockMvc.perform(get("/api/notes/by-patId/{patId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andReturn();
    }

    @Test
    void testGetAllNotesOfPatientPatLastName() throws Exception {
        // Given
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());

        repository.insert(List.of(note1, note2));

        // When
        mockMvc.perform(get("/api/notes/by-lastName/{lastName}", "Julie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment", is("Comment 2")))
                .andReturn();
    }

    @Test
    void testAddNote() throws Exception {
        // When
        Note newNote = new Note(5L, "Tom", "Comment Smoker", LocalDate.now());
        mockMvc.perform(post("/api/notes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNote))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment", is("Comment Smoker")))
                .andReturn();
    }

    @Test
    void testUpdateById() throws Exception {
        // Given
        Note noteSaved = new Note(5L, "Tom", "Comment Smoker", LocalDate.now());
        Note noteReturned = repository.insert(noteSaved);

        Note noteUpdated = new Note(5L, "Tom", "Comment less Smoker", LocalDate.now());
        // When
        mockMvc.perform(put("/api/notes/{id}", noteReturned.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteUpdated))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment", is("Comment less Smoker")))
                .andReturn();
    }

    @Test
    void testDeleteById() throws Exception {
        // Given
        Note noteExisting = new Note(5L, "Tom", "Comment Smoker", LocalDate.now());
        Note noteReturned = repository.insert(noteExisting);
        // When
        mockMvc.perform(delete("/api/notes/{id}", noteReturned.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void testDeleteByIdShouldThrowNoteNoteFoundException() throws Exception {
        String noteId = UUID.randomUUID().toString();
        mockMvc.perform(delete("/api/notes/{id}", noteId))
                .andExpect(status().isNoContent())
                .andReturn();
    }
    @Test
    void testGetNoteById() throws Exception {
        // Given
        Note noteExisting = new Note(5L, "Tom", "Comment Smoker", LocalDate.now());
        Note noteReturned = repository.insert(noteExisting);

        // When
        mockMvc.perform(get("/api/notes/{id}", noteReturned.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patLastName", is("Tom")))
                .andReturn();
    }

    @Test
    void testAddNoteToPatientByPatId() throws Exception {
        // Given
        Note noteExisting = new Note(5L, "Tom", "Comment Smoker", LocalDate.now());
        repository.insert(noteExisting);

        String newNote = "Comment less Smoker";

        mockMvc.perform(post("/api/notes/{patId}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                .param("note",  newNote))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment", is(newNote)))
                .andReturn();
    }
}