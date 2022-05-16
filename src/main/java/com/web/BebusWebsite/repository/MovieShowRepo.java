package com.web.BebusWebsite.repository;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface MovieShowRepo extends CrudRepository<MovieShowEntity, Long> {
    void deleteAllByMovieEntityId(Long id);

    void deleteAllByCinemaHallEntityId(Long id);

    void deleteAllByCinemaEntityId(Long id);

    Iterable<MovieShowEntity> findAllByCinemaEntityId(Long id);

    Iterable<MovieShowEntity> findAllByMovieEntityId(Long id);

    Iterable<MovieShowEntity> findAllByCinemaHallEntityId(Long id);

    Integer countAllByMovieEntityId(Long id);

    Integer countAllByCinemaEntityId(Long id);

    Integer countAllByMovieEntityIdAndCinemaEntityId(Long id, Long id1);

    Integer countAllByCinemaHallEntityIdAndMovieEntityId(Long id, Long id1);

    List<MovieShowEntity> findAllByMovieEntityIdAndCinemaHallEntityId(@Min(1) Long movieEntity_id, @Min(1) Long cinemaHallEntity_id);

//    Iterable<MovieShowEntity> findAllByCinemaHallEntityIdAndMovieEntityAndMovieEntityIgnoreCaseAndCinemaEntityContainsIgnoreCase(@Min(1) Long cinemaHallEntity_id, @NotNull @NotEmpty MovieEntity movieEntity, @NotNull @NotEmpty MovieEntity movieEntity2, @NotNull @NotEmpty CinemaEntity cinemaEntity);
}
