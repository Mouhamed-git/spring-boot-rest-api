package com.monsoko.training.springboot.comicbookslibrary.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import com.monsoko.training.springboot.comicbookslibrary.repository.ComicRepository;

@SpringBootTest
@Transactional
@RequestMapping("springboot/api")
public class ComicServiceImplIT {
	 
	@Autowired
	private ComicService ComicService;
	
	@Autowired
	private ComicRepository comicRepository;
	
	@Test
	public void ShouldGetComicFromBdById() {
		//Given
		Comic blacksad = Comic.builder()
				.title("Blacksad")
				.scriptWriter("Juan Diaz Canales")
				.illustrator("Juanjo Guarnido")
				.publisher("Dargaud")
				.build();
		this.comicRepository.save(blacksad);

		Comic akira = Comic.builder()
				.title("Akira")
				.scriptWriter("Katsuhiro Otomo")
				.illustrator("Katsuhiro Otomo")
				.publisher("Epic Comics")
				.build();
		this.comicRepository.save(akira);

		Comic akira_bis = Comic.builder()
				.title("Akira")
				.scriptWriter("Bill Watterso")
				.illustrator("Bill Watterson")
				.publisher("Andrews McMeel Publishing")
				.build();
		this.comicRepository.save(akira_bis);
		
		//When
		Comic newComic = ComicService.getComicById(blacksad.getId());
		
		//Then
		assertThat(newComic).isEqualTo(blacksad);
	}	
}
