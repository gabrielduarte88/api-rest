package com.zup.comics.comicsmanager.persistence.repositories;

import com.zup.comics.comicsmanager.persistence.models.Comic;
import com.zup.comics.comicsmanager.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {
    @Query(value = "select comic from Comic comic where comic.comicId = :comicId")
    public Comic getByComicId(@Param("comicId") Integer comicId);

    @Query(value = "select comic from Comic comic where comic.user = :user")
    public Page<Comic> listByUserId(@Param("user") User user, Pageable pageable);
}
