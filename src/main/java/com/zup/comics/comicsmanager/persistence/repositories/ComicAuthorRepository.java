package com.zup.comics.comicsmanager.persistence.repositories;

import com.zup.comics.comicsmanager.persistence.models.ComicAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicAuthorRepository extends JpaRepository<ComicAuthor, Integer> {
}
