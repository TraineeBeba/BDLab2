package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.entity.*;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.model.CinemaHall;
import com.web.BebusWebsite.model.Movie;
import com.web.BebusWebsite.service.CinemaHallService;
import com.web.BebusWebsite.service.GoogleChartsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class CinemaHallController {
    @Autowired
    private GoogleChartsUtils gcu;

    @Autowired
    CinemaHallService cinemaHallService;

    @RequestMapping("/hall")
    public String getCinemaHalls(Model model, RedirectAttributes redirectAttributes){

        Iterable<CinemaHallEntity> halls = cinemaHallService.getCinemaHallRepo().findAll();

        model.addAttribute("halls", halls);

        return "/halls/hall";
    }

    @RequestMapping("/hall/create")
    public String getCreateCinemaHall(Model model){
        Iterable<CinemaEntity> cinemas = cinemaHallService.getCinemaRepo().findAll();
        model.addAttribute("cinemas", cinemas);
        return "/halls/create-hall";
    }

    @PostMapping("/hall/create")
    public String createCinemaHall(@Valid CinemaHall hall, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");
        String createHallCheck = cinemaHallService.createHallCheck(hall);
        if (createHallCheck != null){
            redirectAttributes.addFlashAttribute("wrongData", createHallCheck);
            return "redirect:" + referer;
        }

        CinemaEntity cinemaEntity = cinemaHallService.getCinemaRepo().findById(hall.getCinemaEntity().getId()).get();
        CinemaHallEntity hallEntity = new CinemaHallEntity(hall.getNumber(),cinemaEntity, hall.getPlacesCount());
        cinemaHallService.getCinemaHallRepo().save(hallEntity);

        return "redirect:/hall";
    }

    @RequestMapping("/hall/edit")
    public String getEditCinemaHall(@Valid CinemaHall hall, Model model) {
        Iterable<CinemaEntity> cinemas = cinemaHallService.getCinemaRepo().findAll();

        model.addAttribute("id", hall.getId());
        model.addAttribute("number", hall.getNumber());
        model.addAttribute("placesCount", hall.getPlacesCount());
        model.addAttribute("cinemaEntity", hall.getCinemaEntity());
        model.addAttribute("cinemas", cinemas);

        return "/halls/edit-hall";
    }

    @PostMapping("/hall/edit")
    public String editCinemaHall(@Valid CinemaHall hall,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String existenceHallCheck = cinemaHallService.existenceHallCheck(hall);
        if (existenceHallCheck != null){
            redirectAttributes.addFlashAttribute("wrongData",existenceHallCheck);
            return "redirect:"+ referer;
        }

        CinemaHallEntity cinemaHallEntity = cinemaHallService.getCinemaHallRepo().findById(hall.getId()).get();
        CinemaHallEntity returnEntity = new CinemaHallEntity( hall.getNumber(),
                                                              hall.getCinemaEntity(),
                                                              hall.getPlacesCount());

        BeanUtils.copyProperties(returnEntity, cinemaHallEntity, "id");
        cinemaHallService.getCinemaHallRepo().save(cinemaHallEntity);

        return "redirect:/hall";
    }

    @RequestMapping("/hall/delete")
    public String getDeleteCinemaHall(@Valid CinemaHall cinemaHall, Model model){

        model.addAttribute("id", cinemaHall.getId());
        model.addAttribute("number", cinemaHall.getNumber());
        model.addAttribute("cinema", cinemaHall.getCinemaEntity().getName());
        model.addAttribute("placesCount", cinemaHall.getPlacesCount());

        return "/halls/delete-hall";
    }

    @Transactional
    @PostMapping("/hall/delete")
    public String deleteCinemaHall(@Valid CinemaHall cinemaHall, RedirectAttributes redirectAttributes){

        CinemaHallEntity cinemaHallEntity = cinemaHallService.getCinemaHallRepo().findById(cinemaHall.getId()).get();
        int number = cinemaHallEntity.getNumber();
        String cinema = cinemaHallEntity.getCinemaEntity().getName() + "  ";
        cinema += cinemaHallEntity.getCinemaEntity().getAddress();

        redirectAttributes.addFlashAttribute("deleted", "Залу №" + number + " було видалено з кінотеатру " + cinema);
        cinemaHallService.getMovieShowRepo().deleteAllByCinemaHallEntityId(cinemaHall.getId());
        cinemaHallService.getCinemaHallRepo().deleteById(cinemaHall.getId());

        return "redirect:/hall";
    }

    @RequestMapping("/hall/details")
    public String getDetailsCinema(@Valid CinemaHall cinemaHall, Model model) {

        Iterable<MovieEntity> movies = cinemaHallService.getMovieRepo().findAll();
        Iterable<MovieShowEntity> movieShows = cinemaHallService.getMovieShowRepo().findAllByCinemaHallEntityId(cinemaHall.getId());

        model.addAttribute("moviesData", gcu.getCinemaHallMoviesMap(movies, cinemaHall));
        model.addAttribute("number", cinemaHall.getNumber());
        model.addAttribute("cinema", cinemaHall.getCinemaEntity().getName());
        model.addAttribute("placesCount", cinemaHall.getPlacesCount());
        model.addAttribute("movieShows", movieShows);

        return "/halls/details-hall";
    }
}
