package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.model.CinemaHall;
import com.web.BebusWebsite.model.Movie;
import com.web.BebusWebsite.repository.MovieShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class GoogleChartsUtils {

    @Autowired
    private MovieShowRepo movieShowRepo;

    public Map<String, Integer> getMovieShowMoviesMap(Iterable<MovieEntity> movieEntities) {
        Map<String, Integer> moviesMap = new TreeMap<>();
        for (MovieEntity movieEntity : movieEntities) {
            moviesMap.put(movieEntity.getName(), movieShowRepo.countAllByMovieEntityId(movieEntity.getId()));
        }
        return moviesMap;
    }

    public Map<String, Integer> getMovieShowCinemasMap(Iterable<CinemaEntity> cinemaEntities) {
        Map<String, Integer> cinemasMap = new TreeMap<>();
        for (CinemaEntity cinemaEntity : cinemaEntities) {
            cinemasMap.put(cinemaEntity.getName() + "  " + cinemaEntity.getAddress(), movieShowRepo.countAllByCinemaEntityId(cinemaEntity.getId()));
        }
        return cinemasMap;
    }

    public Map<String, Integer> getMovieCinemasMap(Iterable<CinemaEntity> cinemaEntities, Movie movie) {
        Map<String, Integer> cinemas = new TreeMap<>();
        for (CinemaEntity cinemaEntity : cinemaEntities) {
            cinemas.put(cinemaEntity.getName() + "  " + cinemaEntity.getAddress(), movieShowRepo.countAllByMovieEntityIdAndCinemaEntityId(movie.getId(), cinemaEntity.getId()));
        }
        return cinemas;
    }

    public Map<String, Integer> getCinemaHallMoviesMap(Iterable<MovieEntity> movieEntities, CinemaHall cinemaHall) {
        Map<String, Integer> moviesMap = new TreeMap<>();
        for (MovieEntity movieEntity : movieEntities) {
            moviesMap.put(movieEntity.getName(), movieShowRepo.countAllByCinemaHallEntityIdAndMovieEntityId(cinemaHall.getId(), movieEntity.getId()));
        }
        return moviesMap;
    }

    public Map<String, Integer> getCinemaMoviesMap(Iterable<MovieEntity> movies, Cinema cinema) {
        Map<String, Integer> brandsMap = new TreeMap<>();
        for (MovieEntity movieEntity : movies) {
            brandsMap.put(movieEntity.getName(), movieShowRepo.countAllByMovieEntityIdAndCinemaEntityId(movieEntity.getId(), cinema.getId()));
        }
        return brandsMap;
    }

    public Map<String, Integer> getStudioMoviesMap(Iterable<MovieEntity> movieEntities) {
        Map<String, Integer> moviesMap = new TreeMap<>();
        for (MovieEntity movieEntity : movieEntities) {
            moviesMap.put(movieEntity.getName(), movieShowRepo.countAllByMovieEntityId(movieEntity.getId()));
        }
        return moviesMap;
    }

    public Map<String, Integer> getStudioCinemasMap(Iterable<CinemaEntity> cinemaEntities) {
        Map<String, Integer> cinemasMap = new TreeMap<>();
        for (CinemaEntity cinemaEntity : cinemaEntities) {
            cinemasMap.put(cinemaEntity.getName() + "  " + cinemaEntity.getAddress(), movieShowRepo.countAllByCinemaEntityId(cinemaEntity.getId()));
        }
        return cinemasMap;
    }
}
