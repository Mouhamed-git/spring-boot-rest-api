package com.monsoko.training.springboot.comicbookslibrary.repository;

import com.monsoko.training.springboot.comicbookslibrary.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
}
