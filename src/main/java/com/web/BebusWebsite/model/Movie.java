package com.web.BebusWebsite.model;

import com.web.BebusWebsite.entity.StudioEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Movie {
    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String videoTrailerLink;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private StudioEntity studioEntity;

    @NotNull
    @NotEmpty
    private double rating;

    public Movie() {}

    public Movie(@NotNull @NotEmpty String name,
                 @NotNull @NotEmpty String videoTrailerLink,
                 @NotNull @NotEmpty String description,
                 @NotNull @NotEmpty StudioEntity studioEntity,
                 @NotNull @NotEmpty double rating) {
        this.name = name;
        this.videoTrailerLink = videoTrailerLink;
        this.description = description;
        this.studioEntity = studioEntity;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoTrailerLink() {
        return videoTrailerLink;
    }

    public void setVideoTrailerLink(String videoTrailerLink) {
        this.videoTrailerLink = videoTrailerLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StudioEntity getStudioEntity() {
        return studioEntity;
    }

    public void setStudioEntity(StudioEntity studioEntity) {
        this.studioEntity = studioEntity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
