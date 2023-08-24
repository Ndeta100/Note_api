package io.ndeta.springwithnextjs.resource;

import io.ndeta.springwithnextjs.domain.HttpResponse;
import io.ndeta.springwithnextjs.domain.Note;
import io.ndeta.springwithnextjs.enumeration.Level;
import io.ndeta.springwithnextjs.service.NoteService;
import io.ndeta.springwithnextjs.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;


import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "note")
public class NoteResource implements ErrorController {
    private  final NoteService noteService;
    //Getting all notes in the database
    @GetMapping("/all")
    public ResponseEntity<HttpResponse<Note>>getNotes(){
        return  ResponseEntity.ok().body(noteService.getNotes());
    }
    //Creating a note
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<Note>>saveNotes(@RequestBody @Valid Note note){
        return  ResponseEntity.created(URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/notes/all")
                        .toUriString()
        )).body(noteService.saveNotes(note));
    }
    //Filter a note in the database by their levels /filter?level=HIGH
    @GetMapping("/filter")
    public ResponseEntity<HttpResponse<Note>>filterNotes(@RequestParam(value = "level") Level level){
        return  ResponseEntity.ok().body(noteService.filterNotes(level));
    }
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<Note>>updateNotes(@RequestBody @Valid Note note){
        return  ResponseEntity.ok().body(noteService.updateNotes(note));
    }
    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<HttpResponse<Note>>deleteNotes(@PathVariable(value = "noteId") Long id){
        return  ResponseEntity.ok().body(noteService.deleteNotes(id));
    }
    @RequestMapping("/error")
    public ResponseEntity<HttpResponse<?>>handleError(HttpServletRequest request){
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason("There is no mapping for a " + request.getMethod()  + " request for this path on the server")
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                        .build(), NOT_FOUND);
    }


}
