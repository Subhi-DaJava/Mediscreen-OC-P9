package com.oc.practitionernotes.test_repository;

import com.oc.practitionernotes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteTestRepository extends MongoRepository<Note, String> {
    List<Note> findByPatId(Long patId);
    List<Note> findByPatLastName(String lastName);
}
