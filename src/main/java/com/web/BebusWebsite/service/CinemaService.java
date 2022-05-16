package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.repository.CinemaHallRepo;
import com.web.BebusWebsite.repository.CinemaRepo;
import com.web.BebusWebsite.repository.MovieRepo;
import com.web.BebusWebsite.repository.MovieShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private CinemaHallRepo cinemaHallRepo;

    @Autowired
    private MovieShowRepo movieShowRepo;

    @Autowired
    private MovieRepo movieRepo;

    public CinemaRepo getCinemaRepo() {
        return cinemaRepo;
    }

    public String createCinemaCheck(Cinema cinema){
        if (cinemaRepo.findByAddress(cinema.getAddress()) != null) {
            return "Адреса \"" + cinema.getAddress() + "\" вже зайнята";
        }
        return null;
    }

    public String existenceCinemaCheck(Cinema cinema){
        CinemaEntity cinemaEntity = cinemaRepo.findById(cinema.getId()).get();
        CinemaEntity returnEntity = new CinemaEntity(cinema.getName(), cinema.getAddress());

        if (cinemaEntity.getName().equals(returnEntity.getName())){              // Name is the same
            if (cinemaEntity.getAddress().equals(returnEntity.getAddress())) {   // Address also is the same
                return "Змініть хоч щось";
            }

            if(cinemaRepo.findByAddress(returnEntity.getAddress()) != null) {    // Address is not free
                return "Така адреса вже зайнята";
            }

            return null;                                                         // Address is free
        }
                                                                                 // Name is not the same
        if (cinemaEntity.getAddress().equals(returnEntity.getAddress())){        // Address is the same
            return null;                                                         // Just changed the name
        }
                                                                                 // Name and Address are not the same
        if(cinemaRepo.findByAddress(returnEntity.getAddress()) != null) {        // Address is not free
            return "Така адреса вже зайнята";
        }

        return null;                                                             // Address is free
    }

    public CinemaHallRepo getCinemaHallRepo() {
        return cinemaHallRepo;
    }

    public MovieShowRepo getMovieShowRepo() {
        return movieShowRepo;
    }

    public MovieRepo getMovieRepo() {
        return movieRepo;
    }
}
