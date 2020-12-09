package com.monsoko.training.springboot.comicbookslibrary.service;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;
import com.monsoko.training.springboot.comicbookslibrary.domain.exception.RessourceNotFoundException;
import com.monsoko.training.springboot.comicbookslibrary.repository.AlbumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class AlbumServiceimpl implements AlbumService{
	
	@Autowired
    private AlbumRepository albumRepository;
	
    @Override
    public Album saveAlbum(Album album) {
        Assert.notNull(album, "Album connot be null!!!");
        return this.albumRepository.save(album);
    }

    @Override
    public void deleteAlbum(Long id) {
        Assert.notNull(id, "id connot be null");
        Optional<Album> albumObj = this.albumRepository.findById(id);
        if(albumObj.isPresent()) {
        	this.albumRepository.delete(albumObj.get());
       
        }else {
        	throw new RessourceNotFoundException("No album with id"+id);
        }
        	
    }

    @Override
    public Album getAlbumById(Long id) {
        Assert.notNull(id, "id Connot be null");
        Optional<Album> albumObj = this.albumRepository.findById(id);
        	if(albumObj.isPresent()) {
        		return albumObj.get();
        	}else {
        		throw new RessourceNotFoundException("No album with id"+id);
        	}
    }
    
}
