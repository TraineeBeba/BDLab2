package com.web.BebusWebsite.repository;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaHallRepo extends CrudRepository<CinemaHallEntity, Long> {
    CinemaHallEntity findCinemaHallEntityByCinemaEntityAndNumber(CinemaEntity cinemaEntity, int number);

    List<CinemaHallEntity> findCinemaHallEntityByCinemaEntity(CinemaEntity cinemaEntity);

    void deleteAllByCinemaEntityId(Long id);
}
