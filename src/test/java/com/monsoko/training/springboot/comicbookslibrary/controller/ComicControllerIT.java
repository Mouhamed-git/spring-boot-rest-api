package com.monsoko.training.springboot.comicbookslibrary.controller;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import com.monsoko.training.springboot.comicbookslibrary.repository.ComicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class ComicControllerIT {

    private static final String BASE_URI = "/comics";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Comic> json;

    @Autowired
    private JacksonTester<List<Comic>> jsonCollections;

    @Autowired
    private ComicRepository comicRepository;

    @BeforeEach
    public void beforeEach() {
        this.comicRepository.deleteAll();
    }

    @Test
    public void shouldCreateRealisticComicAndReturn201() throws Exception {
        Comic blacksad = Comic.builder().title("Blacksad").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();

        // When
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(blacksad).getJson()));

        // Then
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber());
        assertThat(comicRepository.count()).isEqualTo(1);
    }
    
    @Test
    public void shouldUpdateValidComicAndReturn200() throws Exception {
        // Given
      
    	Comic blacksad = Comic.builder().title("Blacksad").scriptWriter("Juan Diaz Canales").illustrator("Juanjo Guarnido").publisher("Dargaud").build();
        Comic comicToUpdate = Comic.builder().title("Akira").scriptWriter("Bill Watterson").illustrator("Bill Watterson").publisher("Andrews McMeel Publishing").build();
        List<Comic> comics = comicRepository.saveAll(Arrays.asList(blacksad, comicToUpdate));
        assertThat(comicRepository.count()).isEqualTo(comics.size());

        // When
        String greatTitle = "Calvin";
        comicToUpdate.setTitle(greatTitle);
        ResultActions response = mockMvc.perform(put(BASE_URI + "/{id}", comicToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(comicToUpdate).getJson()));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json.write(comicToUpdate).getJson()));
        assertThat(comicRepository.findComicByTitle(greatTitle)).hasSize(1);
    }


    @Test
    public void shouldDeleteComicAndReturn204() throws Exception {
        // Given
        Comic akira = Comic.builder().title("Akira").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();
        Comic blacksad = Comic.builder().title("Blacksad").scriptWriter("Juan Diaz Canales").illustrator("Juanjo Guarnido").publisher("Dargaud").build();Comic calvinAndHobbes = Comic.builder().title("Calvin and Hobbes").scriptWriter("Bill Watterson").illustrator("Bill Watterson").publisher("Andrews McMeel Publishing").build();
        List<Comic> comics = comicRepository.saveAll(Arrays.asList(akira, blacksad));
        assertThat(comicRepository.count()).isEqualTo(comics.size());

        // When
        ResultActions response = mockMvc.perform(delete(BASE_URI + "/{id}", akira.getId()));

        // Then
        response.andExpect(status().isNoContent())
                .andExpect(content().string(""));
        assertThat(comicRepository.existsById(akira.getId())).isFalse();
        assertThat(comicRepository.count()).isEqualTo(comics.size() - 1);
    }
    
    @Test
    public void shouldFindAllComic() throws Exception {
        // Given
        Comic akira = Comic.builder().title("Akira").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();
        Comic blacksad = Comic.builder().title("Blacksad").scriptWriter("Juan Diaz Canales").illustrator("Juanjo Guarnido").publisher("Dargaud").build();
        List<Comic> comics = comicRepository.saveAll(Arrays.asList(akira, blacksad));
        assertThat(comicRepository.count()).isEqualTo(comics.size());

        // When
        ResultActions response = mockMvc.perform(get(BASE_URI));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonCollections.write(comics).getJson()));
    }
    @Test
    public void shouldReturnEmptyListWhenFindUnknownSlugAndReturn200() throws Exception {
        // Given
        // When
        ResultActions response = mockMvc.perform(get(BASE_URI).param("slug", "unknownSlug"));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

  
    @Test
    public void shouldFindComicBySlug() throws Exception {
        // Given
        Comic akira = Comic.builder().title("Akira").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();
        Comic blacksad = Comic.builder().title("Blacksad").scriptWriter("Juan Diaz Canales").illustrator("Juanjo Guarnido").publisher("Dargaud").build();
        List<Comic> comics = comicRepository.saveAll(Arrays.asList(akira, blacksad));
        assertThat(comicRepository.count()).isEqualTo(comics.size());

        // When
        ResultActions response = mockMvc.perform(get(BASE_URI).param("slug", akira.getTitle()));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonCollections.write(Arrays.asList(akira)).getJson()));
    }
    @Test
    public void shouldReturn400WhenCreateInvalidComicNoScriptWriter() throws Exception {
        // Given
        Comic comicWithnoScriptWriter = Comic.builder().title("Blacksad").illustrator("Juanjo Guarnido").publisher("Dargaud").build();

        // When
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(comicWithnoScriptWriter).getJson()));

        // Then
        response.andExpect(status().isBadRequest());
        assertThat(comicRepository.count()).isEqualTo(0);
    }

  

    @Test
    public void shouldReturn400WhenUpdateNoExistingComic() throws Exception {
        // Given
    	  Comic comicWithBadTitle = Comic.builder().title("Blacksad").scriptWriter("Juan Diaz Canales").illustrator("Juanjo Guarnido").publisher("Dargaud").build();
    	  
        // When
        comicWithBadTitle.setTitle("Akira");
        ResultActions response = mockMvc.perform(put(BASE_URI + "/{id}", 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(comicWithBadTitle).getJson()));

        // Then
        response.andExpect(status().isBadRequest());
        assertThat(comicRepository.count()).isEqualTo(0);
    }

    @Test
    public void shouldReturn400WhenUpdateInconsistentComicId() throws Exception {
        // Given
    	 Comic comicWithBadTitle = Comic.builder().title("Akira").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();
        assertThat(comicRepository.existsById(comicWithBadTitle.getId())).isTrue();

        // When
        String greatTitle = "Akira";
        comicWithBadTitle.setTitle(greatTitle);
        ResultActions response = mockMvc.perform(put(BASE_URI + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(comicWithBadTitle).getJson()));

        // Then
        response.andExpect(status().isBadRequest());
        assertThat(comicRepository.findComicByTitle(greatTitle)).isEmpty();
    }

    @Test
    public void shouldReturn400WhenDeleteUnknownComic() throws Exception {
        // When
        ResultActions response = mockMvc.perform(delete(BASE_URI + "/{id}", 0L));

        // Then
        response.andExpect(status().isBadRequest());
    }
    
 
    @Test
    public void shouldReturn400WhenFindInvalidSlug() throws Exception {
    	//Given 
    	Comic akira = Comic.builder().title("Akira").scriptWriter("Katsuhiro Otomo").illustrator("Katsuhiro Otomo").publisher("Epic Comics").build();
        // When
        ResultActions response = mockMvc.perform(get(BASE_URI).param("slug", ""));

        // Then
        response.andExpect(status().isBadRequest());
    }
}