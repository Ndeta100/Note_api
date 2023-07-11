package io.ndeta.springwithnextjs.repo;

import io.ndeta.springwithnextjs.domain.Note;
import io.ndeta.springwithnextjs.enumeration.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findByLevel(Level level);
    void  deleteNoteById(Long id);
}
