package com.web.BebusWebsite.repository;

import com.web.BebusWebsite.entity.CinemaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepo extends CrudRepository<CinemaEntity, Long> {
    CinemaEntity findByAddress(String address);
}
