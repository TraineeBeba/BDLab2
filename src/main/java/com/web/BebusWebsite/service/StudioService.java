package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.StudioEntity;
import com.web.BebusWebsite.model.Studio;
import com.web.BebusWebsite.repository.MovieRepo;
import com.web.BebusWebsite.repository.MovieShowRepo;
import com.web.BebusWebsite.repository.StudioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioService {
    @Autowired
    private StudioRepo studioRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieShowRepo movieShowRepo;

    public StudioRepo getStudioRepo() {
        return studioRepo;
    }

    public MovieRepo getMovieRepo() {
        return movieRepo;
    }

    public MovieShowRepo getMovieShowRepo() {
        return movieShowRepo;
    }

    public String createStudioCheck(Studio studio) {
        if (studioRepo.findByName(studio.getName()) != null) {
            return "Cтудія \"" + studio.getName() + "\" вже існує";
        }
        return null;
    }

    public String existenceStudioCheck(Studio studio) {
        StudioEntity studioEntity = studioRepo.findById(studio.getId()).get();
        StudioEntity returnEntity = new StudioEntity(studio.getName());

        if (studioEntity.getName().equals(returnEntity.getName())) {
            return "Назва збігається зі старою";
        }

        if (studioRepo.findByName(studio.getName()) != null) {
            return "Така студія вже існує";
        }
        return null;
    }
}
