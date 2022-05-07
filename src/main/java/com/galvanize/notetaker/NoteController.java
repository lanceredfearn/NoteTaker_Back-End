package com.galvanize.notetaker;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.el.CompositeELResolver;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/notes")
@CrossOrigin
public class NoteController {

    private final NoteRepository repo;

    public NoteController(NoteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Note> getAllNotes() {
        return repo.findAll();
    }

    @GetMapping("/{Id}")
    public Note getNoteById(@PathVariable Long Id) {
        return repo.findById(Id).get();
    }

    @PostMapping
    public ResponseEntity<String> postNewNote(@RequestBody Note note) {
        repo.save(note);
        return ResponseEntity.ok().body(String.format("Successfully created the new note:\n \n%s", note.getTitle()));
    }

    @PatchMapping("/{Id}")
    public ResponseEntity<String> updateExistingNote(@PathVariable Long Id, @RequestBody Map<String, String> patchMap) {
        Note oldNote = repo.findById(Id).get();
        patchMap.forEach( ( key,value )  -> {
            switch (key) {
                case "title" -> oldNote.setTitle(value);
                case "content" -> oldNote.setContent(value);
            }
            repo.save(oldNote);
        });
        return ResponseEntity.ok().body("Note was successfully updated!");
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteExistingNote(@PathVariable Long Id) {
        repo.deleteById(Id);
        return ResponseEntity.accepted().body("Note was successfully removed!");
    }
}
