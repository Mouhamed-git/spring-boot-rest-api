package com.monsoko.training.springboot.comicbookslibrary.repository;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

@DataJpaTest(showSql = true)
public class AlbumRepositoryIT {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldSaveTrueAlbum() {
        // Given
    	Album album = Album.builder()
				.number(2).
				title("Arctic-Nations")
				.publicationDate(LocalDate.of(1999, Month.FEBRUARY, 10))
				.coverName("blacksad1.jpg")
				.build();

        // When
        albumRepository.save(album);

        // Then
		Assertions.assertThat(entityManager.find(Album.class, album.getId())).isSameAs(album);
    }
    
	@Test
	public void shouldNoSaveAlbumIfNoTitle() {
		// Given
		Album album = Album.builder()
				.number(2).
				title("Arctic-Nations")
				.publicationDate(LocalDate.of(1999, Month.FEBRUARY, 10))
				.coverName("blacksad1.jpg")
				.build();

		// When
		albumRepository.save(album);

		// Then
		assertThat(album.getTitle()).isNotEmpty();
	}
    
}