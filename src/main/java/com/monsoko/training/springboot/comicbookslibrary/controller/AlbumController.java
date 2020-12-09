package com.monsoko.training.springboot.comicbookslibrary.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;
import com.monsoko.training.springboot.comicbookslibrary.service.AlbumServiceimpl;

@CrossOrigin(origins = "*")
@RestController
public class AlbumController {
	
	@Autowired
	private AlbumServiceimpl albumServiceImpl;
	
	@PostMapping("/albums")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Album saveAlbum(@RequestBody Album album)  {
		return this.albumServiceImpl.saveAlbum(album);
	}
	
	@DeleteMapping("/albums/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteAlbum(@PathVariable("id") Long id) {
		this.albumServiceImpl.deleteAlbum(id);
		
	}
	
	@GetMapping("/albums/{id}") 
	@ResponseStatus(code = HttpStatus.OK)
	public Album getAlbumById(@PathVariable("id") Long id) {
		return this.albumServiceImpl.getAlbumById(id);
	}
	
}
