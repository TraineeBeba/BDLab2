package com.web.BebusWebsite.model;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MovieShow {
    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private CinemaEntity cinemaEntity;

    @NotNull
    @NotEmpty
    private MovieEntity movieEntity;

    @NotNull
    @NotEmpty
    private CinemaHallEntity cinemaHallEntity;

    @NotNull
    @NotEmpty
    private int minPrice;

    @NotNull
    @NotEmpty
    private String link;

    public MovieShow() {
    }
    public MovieShow(@NotNull @NotEmpty CinemaEntity cinemaEntity,
                     @NotNull @NotEmpty MovieEntity movieEntity,
                     @NotNull @NotEmpty int minPrice,
                     @NotNull @NotEmpty String link) {
        this.cinemaEntity = cinemaEntity;
        this.movieEntity = movieEntity;
        this.minPrice = minPrice;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CinemaEntity getCinemaEntity() {
        return cinemaEntity;
    }

    public void setCinemaEntity(CinemaEntity cinemaEntity) {
        this.cinemaEntity = cinemaEntity;
    }

    public MovieEntity getMovieEntity() {
        return movieEntity;
    }

    public void setMovieEntity(MovieEntity movieEntity) {
        this.movieEntity = movieEntity;
    }

    public CinemaHallEntity getCinemaHallEntity() {
        return cinemaHallEntity;
    }

    public void setCinemaHallEntity(CinemaHallEntity cinemaHallEntity) {
        this.cinemaHallEntity = cinemaHallEntity;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
