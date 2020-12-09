package com.monsoko.training.springboot.comicbookslibrary.service;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;

import java.util.List;

public interface ComicService {

    Comic saveComic(Comic comic);

    Comic updateComic(Long id, Comic comic);

    void deleteComic(Long id);

    List<Comic> getAllComics();

    Comic getComicById(Long id);

    List<Comic> getComitByTitle(String title);

}
