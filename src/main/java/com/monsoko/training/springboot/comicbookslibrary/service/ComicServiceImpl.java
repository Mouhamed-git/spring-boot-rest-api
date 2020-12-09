package com.monsoko.training.springboot.comicbookslibrary.service;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import com.monsoko.training.springboot.comicbookslibrary.domain.exception.RessourceNotFoundException;
import com.monsoko.training.springboot.comicbookslibrary.repository.ComicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComicServiceImpl implements ComicService {
	
	@Autowired
    private ComicRepository comicRepository;
    @Override
    public Comic saveComic(Comic comic) {
        Assert.notNull(comic, "comic cannot be null!!!");
        return this.comicRepository.save(comic);
    }
    
    @Override
    public Comic updateComic(Long id, Comic comic) {
        Assert.notNull(comic, "comic cannot be null!!!");
        Comic comicDb = this.comicRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("No comic with id "+id));  
        comicDb.setTitle(comic.getTitle());
        comicDb.setFavorite(comic.isFavorite());
        comicDb.setScriptWriter(comic.getScriptWriter());
        comicDb.setIllustrator(comic.getIllustrator());
        comicDb.setPublisher(comic.getPublisher());
        	return this.comicRepository.save(comicDb);
        
    }

    @Override
    public void deleteComic(Long id) {
        Assert.notNull(id, "id must be not null!!!");
        Optional<Comic> comicDb = this.comicRepository.findById(id);
        if(comicDb.isPresent()) {
        	this.comicRepository.delete(comicDb.get());
        }else {
        	throw new RessourceNotFoundException("No comic with id "+id);
        }
        
    }

    @Override
    public List<Comic> getAllComics() {
        return this.comicRepository.findAll();
    }

    @Override
    public Comic getComicById(Long id) {
       Assert.notNull(id,"id cannot be null!!!");
       Optional<Comic> comicObj = this.comicRepository.findById(id);
       if(comicObj.isPresent()) {
    	   return comicObj.get();
       }else {
    	   throw new RessourceNotFoundException("No comic with id"+id);
       }
    }

    @Override
    public List<Comic> getComitByTitle(String title) {
        Assert.hasText(title,"id must be a text");
        return this.comicRepository.findComicByTitle(title);
    }
}
