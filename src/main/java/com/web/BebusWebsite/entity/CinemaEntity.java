package com.web.BebusWebsite.entity;

import com.web.BebusWebsite.model.Cinema;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class CinemaEntity extends Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public CinemaEntity() {super();}

    public CinemaEntity(@NotNull @NotEmpty String name,
                        @NotNull @NotEmpty String address) {
        super(name, address);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(length = 50, nullable = false)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(length = 50, nullable = false, unique = true)
    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }
}

