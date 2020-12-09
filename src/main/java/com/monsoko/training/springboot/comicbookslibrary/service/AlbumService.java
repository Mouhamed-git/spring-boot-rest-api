package com.monsoko.training.springboot.comicbookslibrary.service;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;

public interface AlbumService {
    Album saveAlbum(Album album) ;
    void deleteAlbum(Long id);
    Album getAlbumById(Long id);
}
