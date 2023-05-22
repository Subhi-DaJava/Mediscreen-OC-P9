package com.oc.practitionernotes.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    @NotNull(message = "Patient Id is mandatory")
    @Min(1)
    private Long patId;

    @NotBlank(message = "Patient LastName is mandatory")
    //@Pattern(regexp = "^[A-Z][a-zA-Z]{2,}$", message = "The lastName field must contain at least 3 letters and the first letter should be capital.")
    @Pattern(regexp = "^[A-Z][aA-zA-Z0-9\\s]{2,}$", message = "The lastName field must contain at least 3 letters and the first letter should be capital.")
    private String patLastName;

    private String comment;
    @NotNull(message = "createdAt is mandatory")
    //@Past(message = "createdAt must be in the past")
    private LocalDate createdAt;

    public Note() {
    }

    public Note(Long patId, String patLastName, String comment, LocalDate createdAt) {
        this.patId = patId;
        this.patLastName = patLastName;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public String getPatLastName() {
        return patLastName;
    }

    public void setPatLastName(String patLastName) {
        this.patLastName = patLastName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Note{" +
                "patId=" + patId +
                ", patLastName='" + patLastName + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(patId, note.patId) && Objects.equals(patLastName, note.patLastName) && Objects.equals(comment, note.comment) && Objects.equals(createdAt, note.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patId, patLastName, comment, createdAt);
    }
}
