package io.ndeta.springwithnextjs.service;

import io.ndeta.springwithnextjs.domain.HttpResponse;
import io.ndeta.springwithnextjs.domain.Note;
import io.ndeta.springwithnextjs.enumeration.Level;
import io.ndeta.springwithnextjs.exception.NoteNotFoundException;
import io.ndeta.springwithnextjs.repo.NoteRepo;
import io.ndeta.springwithnextjs.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NoteService {
    private final NoteRepo noteRepo;

    //Get all the notes
    public HttpResponse<Note> getNotes(){
        log.info("Fetching all the notes from the database");
        return HttpResponse.<Note>builder()
                .notes(noteRepo.findAll())
                .message(noteRepo.count()>0 ? noteRepo.count() + " notes retrieved" : "No notes to display")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build();
    }
    //Filter the notes by specific priority
    public HttpResponse<Note> filterNotes(Level level){
        List<Note> notes=noteRepo.findByLevel(level);
        log.info("Fetching all the notes by level {}", level);
        return HttpResponse.<Note>builder()
                .notes(notes)
                .message(notes.size() + " notes are of " + level + " importance")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build();
    }
    //save a note
    public HttpResponse<Note> saveNotes(Note note){
        log.info("Saving  new note to the database");
        note.setCreatedAt(LocalDateTime.now());
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(noteRepo.save(note)))
                .message("Note created successfully")
                .status(CREATED)
                .statusCode(CREATED.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build();
    }
    //delete a note
    public HttpResponse<Note> deleteNotes(Long id) throws  NoteNotFoundException{
        log.info("deleting note from database by id {}", id);
        Optional<Note> optionalNote=Optional.ofNullable(noteRepo.findById(id)
                .orElseThrow(()->new NoteNotFoundException("Note not found in server")));
        optionalNote.ifPresent(noteRepo::delete);
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(optionalNote.get()))
                .message("Note deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build();
    }
    //update a note
    public HttpResponse<Note> updateNotes(Note note) throws  NoteNotFoundException{
        log.info("updating   note to the database");
        Optional<Note> optionalNote=Optional.ofNullable(noteRepo.findById(note.getId())
                .orElseThrow(()->new NoteNotFoundException("Note not found in server")));
        Note updateNote=optionalNote.get();
        updateNote.setId(note.getId());
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());
        updateNote.setLevel(note.getLevel());
        noteRepo.save(updateNote);
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(noteRepo.save(updateNote)))
                .message("Note updated successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build();
    }
}
