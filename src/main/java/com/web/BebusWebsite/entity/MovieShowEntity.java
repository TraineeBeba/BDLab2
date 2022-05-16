package com.web.BebusWebsite.entity;

import com.web.BebusWebsite.model.MovieShow;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class MovieShowEntity extends MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public MovieShowEntity() {}

    public MovieShowEntity(@NotNull @NotEmpty CinemaEntity cinemaEntity,
                           @NotNull @NotEmpty MovieEntity movieEntity,
                           @NotNull @NotEmpty int minPrice,
                           @NotNull @NotEmpty String link) {
        super(cinemaEntity, movieEntity, minPrice, link);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
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

    @ManyToOne
    @Override
    public MovieEntity getMovieEntity() {
        return super.getMovieEntity();
    }

    @Override
    public void setMovieEntity(MovieEntity movieEntity) {
        super.setMovieEntity(movieEntity);
    }

    @OneToOne
    @Override
    public CinemaHallEntity getCinemaHallEntity() {
        return super.getCinemaHallEntity();
    }

    @Override
    public void setCinemaHallEntity(CinemaHallEntity cinemaHallEntity) {
        super.setCinemaHallEntity(cinemaHallEntity);
    }

    @Column(length = 50, nullable = false)
    @Override
    public int getMinPrice() {
        return super.getMinPrice();
    }

    @Override
    public void setMinPrice(int minPrice) {
        super.setMinPrice(minPrice);
    }

    @Column(nullable = false)
    @Override
    public String getLink() {
        return super.getLink();
    }

    @Override
    public void setLink(String link) {
        super.setLink(link);
    }
}
