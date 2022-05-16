package com.web.BebusWebsite.repository;

import com.web.BebusWebsite.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends CrudRepository<MovieEntity, Long> {
    MovieEntity findByName(String name);

    MovieEntity findByVideoTrailerLink(String videoTrailerLink);

    void deleteAllByStudioEntityId(Long id);

    Iterable<MovieEntity> findAllByStudioEntityId(Long id);

}
