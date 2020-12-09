package com.monsoko.training.springboot.comicbookslibrary.repository;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest(showSql = true)
public class ComicRepositoryIT {

	@Autowired
	private ComicRepository comicRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void shouldSaveTrueComic() {
		// Given 
		Comic blacksad = Comic.builder()
				.title("Blacksad")
				.scriptWriter("Juan Diaz Canales")
				.illustrator("Juanjo Guarnido")
				.publisher("Dargaud")
				.build();
				
		// When
		comicRepository.save(blacksad);

		// Then
		assertThat(blacksad.getId()).isNotNull();
		assertThat(entityManager.find(Comic.class, blacksad.getId())).isSameAs(blacksad);
	}

	@Test
	public void shouldFindAllComicsWithSameName() {
		// Given
		Comic akira = Comic.builder()
				.title("Akira")
				.scriptWriter("Katsuhiro Otomo")
				.illustrator("Katsuhiro Otomo")
				.publisher("Epic Comics")
				.build();
		entityManager.persist(akira);
		
		Comic akira_bis = Comic.builder()
				.title("Akira")
				.scriptWriter("Bill Watterso")
				.illustrator("Bill Watterson")
				.publisher("Andrews McMeel Publishing")
				.build();
		entityManager.persist(akira_bis);

		// When
		List<Comic> newComic = comicRepository.findComicByTitle(akira.getTitle());

		// Then
		assertThat(newComic).containsOnly(akira, akira_bis);
	}
}