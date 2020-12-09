package com.monsoko.training.springboot.comicbookslibrary.repository;

import com.monsoko.training.springboot.comicbookslibrary.domain.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository <Comic,Long> {
   List<Comic> findComicByTitle(String title);
}
