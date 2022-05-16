package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.model.Movie;
import com.web.BebusWebsite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MovieService {
    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private StudioRepo studioRepo;

    @Autowired
    private MovieShowRepo movieShowRepo;

    @Autowired
    private CinemaRepo cinemaRepo;

    public MovieRepo getMovieRepo() {
        return movieRepo;
    }

    public StudioRepo getStudioRepo(){
        return studioRepo;
    }

    public String createMovieCheck(Movie movie) {
        if(movieRepo.findByName(movie.getName()) != null) {
            return  "Фільм з таким ім'ям вже існує";
        }

        if(movieRepo.findByVideoTrailerLink(movie.getVideoTrailerLink()) != null) {
            return  "Фільм з таким трейлером вже існує";
        }

        return null;
    }

    public String existenceMovieCheck(Movie movie) {
        MovieEntity movieEntity = movieRepo.findById(movie.getId()).get();
        MovieEntity returnEntity = new MovieEntity( movie.getName(),
                                                    movie.getVideoTrailerLink(),
                                                    movie.getDescription(),
                                                    movie.getStudioEntity(),
                                                    movie.getRating());

        if (movieEntity.getName().equals(returnEntity.getName())){              // Name is the same
            if (movieEntity.getRating() == returnEntity.getRating() &&
                movieEntity.getDescription().equals(returnEntity.getDescription()) &&
                movieEntity.getVideoTrailerLink().equals(returnEntity.getVideoTrailerLink()) &&
                movieEntity.getStudioEntity().getName().equals(returnEntity.getStudioEntity().getName())) {   // Address also is the same

                return "Змініть хоч щось";
            }
            if (!movieEntity.getVideoTrailerLink().equals(returnEntity.getVideoTrailerLink())) {
                if (movieRepo.findByVideoTrailerLink(returnEntity.getVideoTrailerLink()) != null) {    // Address is not free
                    return "Таке посилання вже зайнято";
                }
            }

            return null;                                                         // Address is free
        }
        // Name is not the same
        if (movieEntity.getVideoTrailerLink().equals(returnEntity.getVideoTrailerLink())){        // Address is the same
            return null;                                                         // Just changed the name
        }
        // Name and Address are not the same
        if(movieRepo.findByVideoTrailerLink(returnEntity.getVideoTrailerLink()) != null) {        // Address is not free
            return "Така посилання вже зайнято";
        }

        return null;                                                             // Address is free
    }

    public MovieShowRepo getMovieShowRepo() {
        return movieShowRepo;
    }

    public CinemaRepo getCinemaRepo() {
        return cinemaRepo;
    }
}
