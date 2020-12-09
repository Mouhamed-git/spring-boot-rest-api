package com.monsoko.training.springboot.comicbookslibrary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;
import com.monsoko.training.springboot.comicbookslibrary.repository.AlbumRepository;

@SpringBootTest
@Transactional
public class AlbumServiceimplIT {

	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Test
	public void shouldGetAlbumFromBdById() {
		//Given
		Album album1 = Album.builder()
				.number(1).
				title("Somewhere Within the Shadows")
				.publicationDate(LocalDate.of(2000, Month.JANUARY, 1))
				.coverName("blacksad1.jpg")
				.build();
		
		Album album2 = Album.builder()
				.number(2).
				title("Arctic-Nations")
				.publicationDate(LocalDate.of(1999, Month.FEBRUARY, 10))
				.coverName("blacksad1.jpg")
				.build();
		
		Album album3 = Album.builder()
				.number(3).
				title("Amarillo")
				.publicationDate(LocalDate.of(1992, Month.MAY, 10))
				.coverName("blacksad1.jpg")
				.build();
		 List<Album> albums = albumRepository.saveAll(Arrays.asList(album1, album2, album3));
		   assertThat(albumRepository.findAll()).hasSameElementsAs(albums);
		   
		//When
		Album newComic = this.albumService.getAlbumById(album1.getId());
		
		//Then
		assertThat(newComic).isEqualTo(album1);
	}
	 
}
