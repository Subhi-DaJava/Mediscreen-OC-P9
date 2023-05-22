package com.oc.practitionernotes.service.implemetation;

import com.oc.practitionernotes.model.Note;
import com.oc.practitionernotes.repository.NoteRepository;
import com.oc.practitionernotes.service.NoteService;
import com.oc.practitionernotes.exception.NoteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    private final static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final NoteRepository repository;

    public NoteServiceImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Note> getPatientAllNotesByPatientId(Long patId) {
        logger.debug("getPatientAllNotesByPatientId starts here, from NoteServiceImpl");
        List<Note> notes = repository.findByPatId(patId);
        logger.info("AllNotes of the patient with patId:{} have been successfully retrieved, from NoteServiceImpl", patId);
        return notes;
    }

    @Override
    public List<Note> getPatientAllNotesByPatientLastName(String lastName) {
        logger.debug("getPatientAllNotesByPatientLastName starts here, from NoteServiceImpl");
        List<Note> notes = repository.findByPatLastName(lastName);
        logger.info("AllNotes of the patient with patId:{%s} have been successfully retrieved, from NoteServiceImpl".formatted(lastName));
        return notes;
    }

    @Override
    public Note saveNote(Note note) {
        logger.debug("saveNote method starts here, from NoteServiceImpl");
        Note noteSaved = repository.insert(note);
        logger.info("Note with lastName:{} has been successfully save", note.getPatLastName());
        return noteSaved;
    }

    @Override
    public Note updateNoteById(String id, Note note) {
        logger.debug("updateNoteById method starts here, from NoteServiceImpl");
        Optional<Note> noteById = repository.findById(id);
        if(noteById.isPresent()) {
            Note noteUpdated = noteById.get();
            noteById.get().setPatId(note.getPatId());
            noteById.get().setPatLastName(note.getPatLastName());
            noteById.get().setComment(note.getComment());
            noteById.get().setCreatedAt(note.getCreatedAt());
            repository.save(noteById.get());
            logger.info("Note with id:{} doesn't exist in DB!", id);
            return noteUpdated;
        } else {
            logger.debug("Any note doesn't exist with id:{} in DB!", id);
            throw new NoteNotFoundException("Any note doesn't exist with id:{%s}".formatted(id));
        }

    }

    @Override
    public void deleteById(String id) {
        logger.debug("deleteById method starts here, from NoteServiceImpl");
        Optional<Note> noteById = repository.findById(id);

        if(noteById.isPresent()) {
            logger.info("Note with id:{} has been successfully deleted", id);
            repository.deleteById(id);
        } else {
            logger.debug("Note with id:{} doesn't in DB!", id);
            throw new NoteNotFoundException("Note with id:{%s} doesn't exist in DB!".formatted(id));
        }
    }

    @Override
    public List<Note> getAllNotes() {
        logger.debug("getAllNotes starts here, from NoteServiceImpl");
        List<Note> notes = repository.findAll();
        logger.info("All notes have been successfully retrieved");
        return notes;
    }

    @Override
    public Note getNoteById(String id) {
        logger.debug("getNoteById method starts here, from NoteServiceImpl");
        Optional<Note> noteById = repository.findById(id);
        if(noteById.isPresent()) {
            logger.info("Note with id:{%s} has been successfully retrieved".formatted(id));
            return noteById.get();
        } else {
            logger.error("Note with id:{} not found in DB!, from NoteServiceImpl", id);
            throw new NoteNotFoundException("Note with id:{%s} not found in DB!, from NoteServiceImpl".formatted(id));
        }
    }

    @Override
    public Note addNoteByPatId(Long patId, String note) {
        logger.debug("addNoteByPatId stats here, from NoteServiceImpl");
        List<Note> notesByPatId = repository.findByPatId(patId);
        if(notesByPatId.isEmpty()) {
            logger.error("No note associated with this patId:{%d}".formatted(patId));
            throw new NoteNotFoundException("No note associated with this patId:{%d}".formatted(patId));
        }

        Note newNote = new Note();
        newNote.setPatId(patId);
        newNote.setCreatedAt(LocalDate.now());
        newNote.setComment(note);
        newNote.setPatLastName(notesByPatId.get(0).getPatLastName());

        Note newNoteSaved = repository.insert(newNote);

        logger.info("New Note has been successfully added to the Patient Id:{%d}".formatted(patId));
        return newNoteSaved;
    }
}
