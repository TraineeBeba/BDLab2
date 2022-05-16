package com.web.BebusWebsite.model;

import com.web.BebusWebsite.entity.CinemaEntity;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CinemaHall {
    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private int number;

    @NotNull
    @NotEmpty
    private CinemaEntity cinemaEntity;

    @NotNull
    @NotEmpty
    private int placesCount;

    public CinemaHall() {
    }

    public CinemaHall(@NotNull @NotEmpty int number,
                      @NotNull @NotEmpty CinemaEntity cinemaEntity,
                      @NotNull @NotEmpty int placesCount) {
        this.number = number;
        this.cinemaEntity = cinemaEntity;
        this.placesCount = placesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CinemaEntity getCinemaEntity() {
        return cinemaEntity;
    }

    public void setCinemaEntity(CinemaEntity cinemaEntity) {
        this.cinemaEntity = cinemaEntity;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }
}