package com.monsoko.training.springboot.comicbookslibrary.controller;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;
import com.monsoko.training.springboot.comicbookslibrary.repository.AlbumRepository;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest()
public class AlbumControllerIT {

    private static final String BASE_URI = "/albums";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Album> json;

    @Autowired
    private AlbumRepository repository;

    @BeforeEach
    public void beforeEach() {
        this.repository.deleteAll();
    }

    @Test
    public void shouldCreateBasicAlbumAndReturn201() throws Exception {
        // Given
        Album album = Album.builder().number(1).title("Somewhere Within the Shadows").publicationDate(LocalDate.of(2020, Month.DECEMBER, 1)).coverName("blacksad-1.jpg").build();

        // When
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(album).getJson()));

        // Then
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber());
        assertThat(this.repository.count()).isEqualTo(1L);
    }

    @Test
    public void shouldReturn400WhenCreateInvalidAlbumNoTitle() throws Exception {
        // Given
        Album albumWithnoTitle = Album.builder().number(1).title("").publicationDate(LocalDate.of(2000, Month.NOVEMBER, 1)).coverName("blacksad-1.jpg").build();

        // When
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(albumWithnoTitle).getJson()));

        // Then
        response.andExpect(status().isBadRequest());
        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    public void shouldDeleteAlbumAndReturn204() throws Exception {
        // Given
        Album album1 = Album.builder().number(1).title("Somewhere Within the Shadows").publicationDate(LocalDate.of(2000, Month.JANUARY, 13)).coverName("blacksad-1.jpg").build();
        Album album2 = Album.builder().number(2).title("Arctic-Nation").publicationDate(LocalDate.of(2003, Month.APRIL, 1)).coverName("blacksad-2.jpg").build();
        Album album3 = Album.builder().number(3).title("Red Soul").publicationDate(LocalDate.of(2005, Month.FEBRUARY, 12)).coverName("blacksad-3.jpg").build();
        List<Album> albums = repository.saveAll(Arrays.asList(album1, album2, album3));
        assertThat(repository.count()).isEqualTo(albums.size());

        // When
        ResultActions response = mockMvc.perform(delete(BASE_URI + "/{id}", album1.getId()));

        // Then
        response.andExpect(status().isNoContent())
                .andExpect(content().string(""));
        assertThat(repository.findAll()).containsOnly(album2, album3);
    }
    
    @Test
    public void shouldReturn400WhenDeleteUnknownAlbum() throws Exception {
        // When
        ResultActions response = mockMvc.perform(delete(BASE_URI + "/{id}", 0));

        // Then
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindAlbumByIdAndReturn200() throws Exception {
        // Given
        Album album1 = Album.builder().number(1).title("Somewhere Within the Shadows").publicationDate(LocalDate.of(2000, Month.NOVEMBER, 1)).coverName("blacksad-1.jpg").build();
        Album album2 = Album.builder().number(2).title("Arctic-Nation").publicationDate(LocalDate.of(2003, Month.APRIL, 1)).coverName("blacksad-2.jpg").build();
        List<Album> albums = repository.saveAll(Arrays.asList(album1, album2));
        assertThat(repository.count()).isEqualTo(albums.size());

        // When
        ResultActions response = mockMvc.perform(get(BASE_URI + "/{id}", album1.getId()));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json.write(album1).getJson()));
    }
    
 
    @Test
    public void shouldReturn404WhenFindUnknownAlbumId() throws Exception {
        // When
        ResultActions response = mockMvc.perform(get(BASE_URI + "/{id}", 0));

        // Then
        response.andExpect(status().isNotFound());
    }
}