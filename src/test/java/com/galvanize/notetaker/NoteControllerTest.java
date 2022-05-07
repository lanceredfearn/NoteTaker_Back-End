package com.galvanize.notetaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.transaction.Transactional;
import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class NoteControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    NoteRepository repo;

    Note test1;
    Note test2;
    Note test3;

    @BeforeEach
    void init() {
        test1 = new Note("first test", "data");
        test2 = new Note("second test", "data");
        test3 = new Note("third test", "data");
        repo.save(test1);
        repo.save(test2);
        repo.save(test3);
    }

    private final String uri = "http://localhost:8080/notes";

    @Rollback
    @Transactional
    @Test
    void getAllNotes() throws Exception {
        this.mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Rollback
    @Transactional
    @Test
    void getNoteById() throws Exception {
        this.mvc.perform(get(uri + "/{Id}", test3.getId()))
                .andExpect(jsonPath("$.content", is("data")))
                .andExpect(jsonPath("$.title", is("third test")));
    }

    @Rollback
    @Transactional
    @Test
    void postNewNote() throws Exception {
        this.mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"fourth test\",\n" +
                        " \"content\" : \"data\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully created the new note:\n" +
                        " \n" +
                        "fourth test"));
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingNote() throws Exception {
        this.mvc.perform(patch(uri + "/{Id}", test3.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"patch test\",\n" +
                        " \"content\" : \"data\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("Note was successfully updated!"));
    }

    @Rollback
    @Transactional
    @Test
    void deleteExistingNote() throws Exception{
        this.mvc.perform(delete(uri + "/{Id}", test3.getId()))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Note was successfully removed!"));
    }
}
