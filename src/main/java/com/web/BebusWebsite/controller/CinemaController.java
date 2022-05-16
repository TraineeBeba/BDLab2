package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.model.CinemaHall;
import com.web.BebusWebsite.service.CinemaService;
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
public class CinemaController {
    @Autowired
    private GoogleChartsUtils gcu;

    @Autowired
    private CinemaService cinemaService;

    @RequestMapping("/cinema")
    public String getCinemas(Model model){

        Iterable<CinemaEntity> cinemas = cinemaService.getCinemaRepo().findAll();
        model.addAttribute("cinemas", cinemas);

        return "cinemas/cinema";
    }

    @RequestMapping("/cinema/create")
    public String getCreateCinema(Model model){
        return "/cinemas/create-cinema";
    }

    @PostMapping("/cinema/create")
    public String createCinema(@Valid Cinema cinema, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String createCinemaCheck = cinemaService.createCinemaCheck(cinema);
        if (createCinemaCheck != null){
            redirectAttributes.addFlashAttribute("wrongData", createCinemaCheck);
            return "redirect:"+ referer;
        }

        CinemaEntity cinemaEntity = new CinemaEntity(cinema.getName(), cinema.getAddress());
        cinemaService.getCinemaRepo().save(cinemaEntity);

        return "redirect:/cinema";
    }

    @RequestMapping("/cinema/edit")
    public String getEditCinema(@Valid Cinema cinema, Model model) {

        model.addAttribute("id", cinema.getId());
        model.addAttribute("name", cinema.getName());
        model.addAttribute("address", cinema.getAddress());

        return "/cinemas/edit-cinema";
    }

    @PostMapping("/cinema/edit")
    public String editCinema(@Valid Cinema cinema,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String existCinemaCheck = cinemaService.existenceCinemaCheck(cinema);
        if (existCinemaCheck != null){
            redirectAttributes.addFlashAttribute("wrongData",existCinemaCheck);
            return "redirect:"+ referer;
        }

        CinemaEntity cinemaEntity = cinemaService.getCinemaRepo().findById(cinema.getId()).get();
        CinemaEntity returnEntity = new CinemaEntity(cinema.getName(), cinema.getAddress());

        BeanUtils.copyProperties(returnEntity, cinemaEntity, "id");
        cinemaService.getCinemaRepo().save(cinemaEntity);

        return "redirect:/cinema";
    }

    @RequestMapping("/cinema/delete")
    public String getDeleteCinema(@Valid Cinema cinema, Model model){

        model.addAttribute("id", cinema.getId());
        model.addAttribute("name", cinema.getName());
        model.addAttribute("address", cinema.getAddress());

        return "/cinemas/delete-cinema";
    }

    @Transactional
    @PostMapping("/cinema/delete")
    public String deleteCinema(@Valid Cinema cinema, RedirectAttributes redirectAttributes){

        CinemaEntity cinemaEntity = cinemaService.getCinemaRepo().findById(cinema.getId()).get();
        String nameAndAddress = cinemaEntity.getName() + "  ";
        nameAndAddress += cinemaEntity.getAddress();

        redirectAttributes.addFlashAttribute("deleted", "Кінотеатр \"" + nameAndAddress + "\" було видалено.");
        cinemaService.getCinemaHallRepo().deleteAllByCinemaEntityId(cinema.getId());
        cinemaService.getMovieShowRepo().deleteAllByCinemaEntityId(cinema.getId());
        cinemaService.getCinemaRepo().deleteById(cinema.getId());

        return "redirect:/cinema";
    }

    @RequestMapping("/cinema/details")
    public String getDetailsCinema(@Valid Cinema cinema, Model model) {

        Iterable<MovieEntity> movies = cinemaService.getMovieRepo().findAll();
        Iterable<MovieShowEntity> movieShows = cinemaService.getMovieShowRepo().findAllByCinemaEntityId(cinema.getId());

        model.addAttribute("moviesData", gcu.getCinemaMoviesMap(movies, cinema));
        model.addAttribute("name", cinema.getName());
        model.addAttribute("address", cinema.getAddress());
        model.addAttribute("movieShows", movieShows);

        return "/cinemas/details-cinema";
    }
}
