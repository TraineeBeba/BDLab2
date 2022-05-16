package com.web.BebusWebsite.entity;

import com.web.BebusWebsite.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class MovieEntity extends Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public MovieEntity() {super();}

    public MovieEntity(@NotNull @NotEmpty String name,
                       @NotNull @NotEmpty String videoTrailerLink,
                       @NotNull @NotEmpty String description,
                       @NotNull @NotEmpty StudioEntity studioEntity,
                       @NotNull @NotEmpty double rating) {
        super(name, videoTrailerLink, description, studioEntity, rating);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false, unique = true)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(nullable = false, unique = true)
    @Override
    public String getVideoTrailerLink() {
        return super.getVideoTrailerLink();
    }

    @Override
    public void setVideoTrailerLink(String videoTrailerLink) {
        super.setVideoTrailerLink(videoTrailerLink);
    }

    @Column(length = 50, nullable = false)
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @OneToOne
    @Override
    public StudioEntity getStudioEntity() {
        return super.getStudioEntity();
    }

    @Override
    public void setStudioEntity(StudioEntity studioEntity) {
        super.setStudioEntity(studioEntity);
    }

    @Column(length = 50, nullable = false)
    @Override
    public double getRating() {
        return super.getRating();
    }

    @Override
    public void setRating(double rating) {
        super.setRating(rating);
    }
}


