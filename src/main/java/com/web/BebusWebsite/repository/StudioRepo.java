package com.web.BebusWebsite.repository;

import com.web.BebusWebsite.entity.StudioEntity;
import org.springframework.data.repository.CrudRepository;

public interface StudioRepo extends CrudRepository<StudioEntity, Long> {
    StudioEntity findByName(String name);
}
