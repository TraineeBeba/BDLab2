package com.web.BebusWebsite.service;

import com.web.BebusWebsite.controller.MovieShowController;
import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import com.web.BebusWebsite.model.MovieShow;
import com.web.BebusWebsite.repository.CinemaHallRepo;
import com.web.BebusWebsite.repository.CinemaRepo;
import com.web.BebusWebsite.repository.MovieRepo;
import com.web.BebusWebsite.repository.MovieShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.web.BebusWebsite.controller.MovieShowController.*;

@Service
public class MovieShowService {
    @Autowired
    private MovieShowRepo movieShowRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private CinemaHallRepo cinemaHallRepo;

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private CinemaHallService cinemaHallService;


    public MovieShowRepo getMovieShowRepo(){
        return movieShowRepo;
    }

    public MovieRepo getMovieRepo() {
        return movieRepo;
    }

    public CinemaHallRepo getHallRepo() {
        return cinemaHallRepo;
    }

    public CinemaRepo getCinemaRepo() {
        return cinemaRepo;
    }

    public String createMovieShowCheck(MovieShow movieShow) {

        CinemaEntity cinemaEntity = cinemaRepo.findById(movieShow.getCinemaEntity().getId()).get();
        MovieEntity movieEntity = movieRepo.findById(movieShow.getMovieEntity().getId()).get();
        MovieShowController.movieShowEntity = new MovieShowEntity(cinemaEntity, movieEntity, movieShow.getMinPrice(), movieShow.getLink());


        // Find all halls from cinema
        Iterable<CinemaHallEntity> cinemaHalls = cinemaHallService.getCinemaHallRepo()
                .findCinemaHallEntityByCinemaEntity(cinemaEntity);

        // check if there one or more halls free
        MyCinemaHallsList.clear();
        for (CinemaHallEntity cinemaHall : cinemaHalls) {
            if (getMovieShowRepo().
                    findAllByMovieEntityIdAndCinemaHallEntityId(movieEntity.getId(), cinemaHall.getId()).isEmpty()){
                MyCinemaHallsList.add(cinemaHall);
            }
        }

        if(MyCinemaHallsList.isEmpty()){
            return "В усіх залах кінотеатру \"" + cinemaEntity.getName() + "  "
                    + cinemaEntity.getAddress()  + "\" вже йде фільм \"" + movieEntity.getName() + "\"";
        }

        return null;
    }

    public String editMovieShowCheck(MovieShow movieShow) {
        MyCinemaHallsList.clear();
        if(returnEntity.getMovieEntity().getId() == movieShowEntity.getMovieEntity().getId()
            && returnEntity.getCinemaEntity().getId() == movieShowEntity.getCinemaEntity().getId()){
            if(returnEntity.getMinPrice() != movieShowEntity.getMinPrice()
                || !returnEntity.getLink().equals(movieShowEntity.getLink())) {
                MyCinemaHallsList.add(movieShowEntity.getCinemaHallEntity());
            }
        }

        // Find all halls from input cinema
        Iterable<CinemaHallEntity> cinemaHalls = cinemaHallService.getCinemaHallRepo()
                .findCinemaHallEntityByCinemaEntity(returnEntity.getCinemaEntity());

        // check if there one or more halls free
        for (CinemaHallEntity cinemaHall : cinemaHalls) {
            if (getMovieShowRepo().
                    findAllByMovieEntityIdAndCinemaHallEntityId(returnEntity.getMovieEntity().getId(), cinemaHall.getId()).isEmpty()){
                MyCinemaHallsList.add(cinemaHall);
            }
        }

        if(MyCinemaHallsList.isEmpty()){
            if(returnEntity.getMovieEntity().getName().equals(movieShowEntity.getMovieEntity().getName())
                    && returnEntity.getCinemaEntity().getAddress().equals(movieShowEntity.getCinemaEntity().getAddress())
                    && returnEntity.getLink().equals(movieShowEntity.getLink())
                    && returnEntity.getMinPrice() == movieShowEntity.getMinPrice()) {

                return "Змініть хоч щось!";
            }

            return "В усіх залах кінотеатру \"" + returnEntity.getCinemaEntity().getName() + "  "
                    + returnEntity.getCinemaEntity().getAddress()  + "\" вже йде фільм \"" + returnEntity.getMovieEntity().getName() + "\"";
        }

        return null;
    }
}
