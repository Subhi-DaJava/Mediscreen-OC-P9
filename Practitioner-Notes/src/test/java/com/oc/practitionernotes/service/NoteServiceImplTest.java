package com.oc.practitionernotes.service;

import com.oc.practitionernotes.exception.NoteNotFoundException;
import com.oc.practitionernotes.model.Note;
import com.oc.practitionernotes.repository.NoteRepository;
import com.oc.practitionernotes.service.implemetation.NoteServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {
    @Mock
    private NoteRepository repository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    void testGetPatientAllNotesByPatientId() {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());
        expectedNotes = List.of(note1, note2);

        when(repository.findByPatId(anyLong())).thenReturn(expectedNotes);

        // When
        List<Note> notesByPatId = noteService.getPatientAllNotesByPatientId(1L);

        // Then
        assertThat(notesByPatId.get(0).getPatLastName()).isEqualTo("Jean");
        assertThat(notesByPatId.size()).isEqualTo(2);

    }
    @Test
    void testGetPatientAllNotesByPatientIdShouldReturnEmptyList() {
        // Given
        when(repository.findByPatId(anyLong())).thenReturn(new ArrayList<>());

        // When
        List<Note> notesByPatId = noteService.getPatientAllNotesByPatientId(1L);

        // Then
        assertThat(notesByPatId.size()).isEqualTo(0);
        assertThat(notesByPatId.isEmpty()).isTrue();

    }
    @Test
    void testGetPatientAllNotesByPatientLastName() {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());
        expectedNotes = List.of(note1, note2);

        when(repository.findByPatLastName(anyString())).thenReturn(expectedNotes);

        // When
        List<Note> notesByPatLastName = noteService.getPatientAllNotesByPatientLastName("Julie");

        // Then
        assertThat(notesByPatLastName.get(0).getPatLastName()).isEqualTo("Jean");
        assertThat(notesByPatLastName.size()).isEqualTo(2);
    }

    @Test
    void testGetPatientAllNotesByPatientLatNameShouldReturnEmptyList() {
        // Given
        when(repository.findByPatLastName(anyString())).thenReturn(new ArrayList<>());

        // When
        List<Note> notesByPatLastName = noteService.getPatientAllNotesByPatientLastName("AnyName");

        // Then
        assertThat(notesByPatLastName.size()).isEqualTo(0);
        assertThat(notesByPatLastName.isEmpty()).isTrue();

    }
    @Test
    void testSaveNote() {
        // Given
        Note noteToSaved = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        Note savedNote = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        savedNote.setId(UUID.randomUUID().toString());
        when(repository.insert(any(Note.class))).thenReturn(savedNote);

        // When
        Note returnedNote = noteService.saveNote(noteToSaved);

        // Then
        assertThat(returnedNote.getPatLastName()).isEqualTo(noteToSaved.getPatLastName());
        assertThat(returnedNote.getComment()).isEqualTo("Comment And Somme Comment");
        assertThat(returnedNote).isEqualTo(savedNote);
    }

    @Test
    void testUpdateNoteById() {
        // Given
        Note noteToUpdate = new Note(1L, "Jean", "Comment And Somme Comment Updated", LocalDate.now());

        Note existingNote = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        String noteId = UUID.randomUUID().toString();
        existingNote.setId(noteId);

        when(repository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(repository.save(any(Note.class))).thenReturn(noteToUpdate);

        // When
        Note returnedUpdatedNote = noteService.updateNoteById(noteId, noteToUpdate);

        // Then
        assertThat(returnedUpdatedNote.getComment()).isEqualTo("Comment And Somme Comment Updated");
    }

    @Test
    void testUpdateNoteByIdShouldThrowNoteNotFoundException() {
        // Given
        String noteId = UUID.randomUUID().toString();
        when(repository.findById(noteId)).thenReturn(Optional.empty());
        // When


        // Then
        assertThatThrownBy(() -> noteService.updateNoteById(noteId, any(Note.class)))
                .isInstanceOf(NoteNotFoundException.class)
                .hasMessageContaining(("Any note doesn't exist with id:{%s}".formatted(noteId)));

        assertThrows(NoteNotFoundException.class, () -> noteService.updateNoteById(noteId, new Note()));
    }


    @Test
    void testDeleteByIdWithSuccess() {
        // Given
        Note existingNote = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        String noteId = UUID.randomUUID().toString();
        existingNote.setId(noteId);
        when(repository.findById(noteId)).thenReturn(Optional.of(existingNote));
        doNothing().when(repository).deleteById(noteId);
        // When
        noteService.deleteById(noteId);

        // Then
        assertThat(repository.findAll().size()).isEqualTo(0);
        verify(repository, times(1)).deleteById(noteId);
    }
    @Test
    void testDeleteByIdShouldThrowNoteNotFoundException() {
        // Given
        String noteId = UUID.randomUUID().toString();
        when(repository.findById(noteId)).thenReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> noteService.deleteById(noteId))
                .isInstanceOf(NoteNotFoundException.class)
                        .hasMessageContaining("Note with id:{%s} doesn't exist in DB!".formatted(noteId));

        // Then
        verify(repository, never()).deleteById(noteId);
    }
    @Test
    void testGetAllNotes() {
        // Given
        List<Note> expectedNotes;
        Note note1 = new Note(1L, "Jean", "Comment 1", LocalDate.now());
        Note note2 = new Note(2L, "Julie", "Comment 2", LocalDate.now());
        String noteId1 = UUID.randomUUID().toString();
        note1.setId(noteId1);
        String noteId2 = UUID.randomUUID().toString();
        note2.setId(noteId2);
        expectedNotes = List.of(note1, note2);

        when(repository.findAll()).thenReturn(expectedNotes);

        // When
        List<Note> returnedNotes = noteService.getAllNotes();

        // Then
        assertThat(returnedNotes.size()).isEqualTo(2);
        assertThat(returnedNotes.get(0).getPatLastName()).isEqualTo("Jean");
    }
    @Test
    void testGetAllNotesShouldReturnEmptyList() {
        // Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<Note> returnedNotes = noteService.getAllNotes();

        // Then
        assertThat(returnedNotes.size()).isEqualTo(0);
    }
    @Test
    void testGetNoteByIdWithSuccess() {
        // Given
        Note existingNote = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        String noteId = UUID.randomUUID().toString();
        existingNote.setId(noteId);
        when(repository.findById(anyString())).thenReturn(Optional.of(existingNote));

        // When
        Note noteGetById = noteService.getNoteById(noteId);

        // Then
        assertThat(noteGetById.getPatLastName()).isEqualTo("Jean");
    }
    @Test
    void testGetNoteByIdShouldThrowNoteNoteFoundException() {
        // Given
        String noteId = UUID.randomUUID().toString();
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> noteService.getNoteById(noteId))
                .isInstanceOf(NoteNotFoundException.class)
                .hasMessageContaining("Note with id:{%s} not found in DB!, from NoteServiceImpl".formatted(noteId));
    }
    @Test
    void addNoteByPatId() {
        // Given
        Note existingNote = new Note(1L, "Jean", "Comment And Somme Comment", LocalDate.now());
        String noteId = UUID.randomUUID().toString();
        existingNote.setId(noteId);


        Note newNoteToAdd = new Note();
        newNoteToAdd.setPatId(existingNote.getPatId());
        newNoteToAdd.setCreatedAt(LocalDate.now());
        newNoteToAdd.setComment("Smoker, Hemoglobin");
        newNoteToAdd.setPatLastName(existingNote.getPatLastName());

        List<Note> notesExpectedByPatId = List.of(existingNote);

        when(repository.findByPatId(anyLong())).thenReturn(notesExpectedByPatId);
        when(repository.insert(newNoteToAdd)).thenReturn(newNoteToAdd);

        // When
        Note noteReturned = noteService.addNoteByPatId(1L, "Smoker, Hemoglobin");

        // Then
        assertThat(noteReturned.getPatLastName()).isEqualTo("Jean");
        assertThat(noteReturned.getComment()).isEqualTo(newNoteToAdd.getComment());
        verify(repository).findByPatId(anyLong());
        verify(repository).insert(newNoteToAdd);
    }

    @Test
    void addNoteByPatIdShouldThrowNoteNotFoundException() {
        // Given
        Long patId = 20L;
        when(repository.findByPatId(patId)).thenReturn(new ArrayList<>());

        // Then
        assertThatThrownBy(() -> noteService.addNoteByPatId(patId, anyString()))
                .isInstanceOf(NoteNotFoundException.class)
                .hasMessageContaining("No note associated with this patId:{%d}".formatted(patId));

    }
}