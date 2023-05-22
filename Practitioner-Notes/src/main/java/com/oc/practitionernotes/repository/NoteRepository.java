package com.oc.practitionernotes.repository;

import com.oc.practitionernotes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatId(Long patId);
    List<Note> findByPatLastName(String lastName);

}
