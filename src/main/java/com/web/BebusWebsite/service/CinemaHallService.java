package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.model.CinemaHall;
import com.web.BebusWebsite.model.Movie;
import com.web.BebusWebsite.repository.CinemaHallRepo;
import com.web.BebusWebsite.repository.CinemaRepo;
import com.web.BebusWebsite.repository.MovieRepo;
import com.web.BebusWebsite.repository.MovieShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CinemaHallService {
    @Autowired
    private CinemaHallRepo cinemaHallRepo;

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private MovieShowRepo movieShowRepo;

    @Autowired
    private MovieRepo movieRepo;

    public CinemaHallRepo getCinemaHallRepo() {
        return cinemaHallRepo;
    }

    public CinemaRepo getCinemaRepo() {
        return cinemaRepo;
    }

    public MovieShowRepo getMovieShowRepo() {
        return movieShowRepo;
    }

    public String createHallCheck(CinemaHall hall) {
        if(cinemaHallRepo.findCinemaHallEntityByCinemaEntityAndNumber(hall.getCinemaEntity(), hall.getNumber()) != null) {
            return "В кінотеатрі \"" + hall.getCinemaEntity().getName() + "  "
                    + hall.getCinemaEntity().getAddress() + "\" зала з номером \"" + hall.getNumber() + "\" вже існує";
        }

        return null;
    }

    public String existenceHallCheck(CinemaHall hall) {
        CinemaHallEntity cinemaHallEntity = cinemaHallRepo.findById(hall.getId()).get();
        CinemaHallEntity returnEntity = new CinemaHallEntity( hall.getNumber(),
                hall.getCinemaEntity(),
                hall.getPlacesCount());

        if (cinemaHallEntity.getCinemaEntity().getId() == returnEntity.getCinemaEntity().getId()){      // Cinema is the same
            if (cinemaHallEntity.getNumber() == returnEntity.getNumber()){                              // Number also is the same
                if (cinemaHallEntity.getPlacesCount() == returnEntity.getPlacesCount()){                // Places count also is the same
                    return "Змініть хоч щось";
                }

                return null;
            }

            if (cinemaHallRepo.findCinemaHallEntityByCinemaEntityAndNumber( returnEntity.getCinemaEntity(),
                                                                            returnEntity.getNumber()) != null) {
                return "Такий номер залу вже зайнятий";
            }

            return null;
        }
        // Name is not the same
        if (cinemaHallRepo.findCinemaHallEntityByCinemaEntityAndNumber( returnEntity.getCinemaEntity(),
                                                                        returnEntity.getNumber()) != null){        // Address is the same
            return "Такий номер залу вже зайнятий";                                                         // Just changed the name
        }

        return null;                                                             // Address is free
    }

    public MovieRepo getMovieRepo() {
        return movieRepo;
    }
}
