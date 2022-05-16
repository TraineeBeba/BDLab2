package com.web.BebusWebsite.entity;

import com.web.BebusWebsite.model.CinemaHall;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class CinemaHallEntity extends CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public CinemaHallEntity() {
    }

    public CinemaHallEntity(@NotNull @NotEmpty int number,
                            @NotNull @NotEmpty CinemaEntity cinemaEntity,
                            @NotNull @NotEmpty int placesCount) {
        super(number, cinemaEntity, placesCount);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false)
    @Override
    public int getNumber() {
        return super.getNumber();
    }

    @Override
    public void setNumber(int number) {
        super.setNumber(number);
    }

    @ManyToOne
    @Override
    public CinemaEntity getCinemaEntity() {
        return super.getCinemaEntity();
    }

    @Override
    public void setCinemaEntity(CinemaEntity cinemaEntity) {
        super.setCinemaEntity(cinemaEntity);
    }

    @Column(length = 50, nullable = false)
    @Override
    public int getPlacesCount() {
        return super.getPlacesCount();
    }

    @Override
    public void setPlacesCount(int placesCount) {
        super.setPlacesCount(placesCount);
    }
}
