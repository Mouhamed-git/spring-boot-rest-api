package com.monsoko.training.springboot.comicbookslibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import com.monsoko.training.springboot.comicbookslibrary.service.ComicServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class ComicController {
	
	@Autowired
	private ComicServiceImpl comicServiceImpl;
	
	@PostMapping("/comics")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Comic saveComic(@RequestBody Comic comic) {
		return this.comicServiceImpl.saveComic(comic);
	}
	
	@GetMapping("/comics")
	public List<Comic> getAllComic() {
		return this.comicServiceImpl.getAllComics();
	}
	
	@GetMapping("/comics/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Comic getComitById(@PathVariable("id") Long id) {
		return this.comicServiceImpl.getComicById(id);
	}
	
	@PutMapping("/comics/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Comic updateComic(@PathVariable("id") Long id, @RequestBody Comic comic) {
		return comicServiceImpl.updateComic(id, comic);
	}
	
	@DeleteMapping("/comics/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteComic(@PathVariable("id") Long id) {
		this.comicServiceImpl.deleteComic(id);
	}
	
	@GetMapping(params = "slug")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Comic> getComitByTitle(String slug) {
		return this.comicServiceImpl.getComitByTitle(slug);
	}
}
