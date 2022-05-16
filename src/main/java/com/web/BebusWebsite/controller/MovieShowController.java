package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import com.web.BebusWebsite.model.MovieShow;
import com.web.BebusWebsite.service.GoogleChartsUtils;
import com.web.BebusWebsite.service.MovieShowService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieShowController {
    @Autowired
    private MovieShowService movieShowService;

    @Autowired
    private GoogleChartsUtils gcu;

    public static MovieShowEntity movieShowEntity;

    public static MovieShowEntity returnEntity;

    public static List<CinemaHallEntity> MyCinemaHallsList = new ArrayList<>();


    @RequestMapping("/movie-show")
    public String getMovieShows(Model model){

        Iterable<MovieEntity> movies = movieShowService.getMovieRepo().findAll();
        Iterable<CinemaEntity> cinemas = movieShowService.getCinemaRepo().findAll();
        Iterable<MovieShowEntity> movieShows = movieShowService.getMovieShowRepo().findAll();

        model.addAttribute("moviesData", gcu.getMovieShowMoviesMap(movies));
        model.addAttribute("cinemasData", gcu.getMovieShowCinemasMap(cinemas));
        model.addAttribute("movieShows", movieShows);

        return "movie_shows/movie_show";
    }

    @RequestMapping("/movie-show/create")
    public String getCreateMovieShow(Model model){
        Iterable<MovieEntity> movies = movieShowService.getMovieRepo().findAll();
        Iterable<CinemaEntity> cinemas = movieShowService.getCinemaRepo().findAll();

        model.addAttribute("movies", movies);
        model.addAttribute("cinemas", cinemas);

        return "/movie_shows/create-movie_show";
    }

    @PostMapping("/movie-show/create")
    public String createMovieShow(@Valid MovieShow movieShow, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String checkMovieShowCreate = movieShowService.createMovieShowCheck(movieShow);
        if (checkMovieShowCreate != null){

            redirectAttributes.addFlashAttribute("wrongData", checkMovieShowCreate);
            return "redirect:"+ referer;
        }

        return "redirect:/movie-show/create-next";
    }

    @RequestMapping("/movie-show/create-next")
    public String getMovieShow(Model model){

//        MyCinemaHallsList = movieShowService.getHallRepo().findCinemaHallEntityByCinemaEntity(movieShowEntity.getCinemaEntity());

        model.addAttribute("cinemaHalls", MyCinemaHallsList);

        return "/movie_shows/create-movie_show_next";
    }

    @PostMapping("/movie-show/create-next")
    public String createMovieShowNext(@Valid MovieShow movieShow, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        CinemaHallEntity cinemaHallEntity = movieShowService.getHallRepo().
                findById(movieShow.getCinemaHallEntity().getId()).get();

        movieShowEntity.setCinemaHallEntity(cinemaHallEntity);
        movieShowService.getMovieShowRepo().save(movieShowEntity);

        return "redirect:/movie-show";
    }

    @RequestMapping("/movie-show/delete")
    public String getDeleteMovieShow(@Valid MovieShow movieShow, Model model){

        model.addAttribute("id", movieShow.getId());
        model.addAttribute("name", movieShow.getMovieEntity().getName());
        model.addAttribute("cinema", movieShow.getCinemaEntity().getName() + " " + movieShow.getCinemaEntity().getAddress());
        model.addAttribute("rating", movieShow.getMovieEntity().getRating());
        model.addAttribute("trailer", movieShow.getMovieEntity().getVideoTrailerLink());
        model.addAttribute("hall", movieShow.getCinemaHallEntity().getNumber());
        model.addAttribute("placesCount", movieShow.getCinemaHallEntity().getPlacesCount());
        model.addAttribute("minPrice", movieShow.getMinPrice());

        return "/movie_shows/delete-movie_show";
    }

    @Transactional
    @PostMapping("/movie-show/delete")
    public String deleteMovieShow(@Valid MovieShow movieShow, RedirectAttributes redirectAttributes){

        MovieShowEntity movieShowEntity = movieShowService.getMovieShowRepo().findById(movieShow.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Кінопоказ фільму \"" + movieShowEntity.getMovieEntity().getName() + "\" було видалено.");

        movieShowService.getMovieShowRepo().deleteById(movieShow.getId());

        return "redirect:/movie-show";
    }

    @RequestMapping("/movie-show/edit")
    public String getEditMovieShow(@Valid MovieShow movieShow, Model model){
        Iterable<MovieEntity> movies = movieShowService.getMovieRepo().findAll();
        Iterable<CinemaEntity> cinemas = movieShowService.getCinemaRepo().findAll();
        int minPrice = movieShow.getMinPrice();
        String link = movieShow.getLink();

        model.addAttribute("id", movieShow.getId());
        model.addAttribute("movies", movies);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("link", link);
        model.addAttribute("minPrice", minPrice);

        return "/movie_shows/edit-movie_show";
    }

    @PostMapping("/movie-show/edit")
    public String editMovieShow(@Valid MovieShow movieShow, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        CinemaEntity cinemaEntity = movieShowService.getCinemaRepo().findById(movieShow.getCinemaEntity().getId()).get();
        MovieEntity movieEntity = movieShowService.getMovieRepo().findById(movieShow.getMovieEntity().getId()).get();

        returnEntity = new MovieShowEntity(cinemaEntity, movieEntity, movieShow.getMinPrice(), movieShow.getLink());
        movieShowEntity = movieShowService.getMovieShowRepo().findById(movieShow.getId()).get();

        String referer = request.getHeader("Referer");
        String checkMovieShowEdit = movieShowService.editMovieShowCheck(movieShow);
        if (checkMovieShowEdit != null){
            redirectAttributes.addFlashAttribute("wrongData", checkMovieShowEdit);
            return "redirect:"+ referer;
        }
        return "redirect:/movie-show/edit-next";
    }

    @RequestMapping("/movie-show/edit-next")
    public String editMovieShowNext(Model model){

//        MyCinemaHallsList = movieShowService.getHallRepo().findCinemaHallEntityByCinemaEntity(movieShowEntity.getCinemaEntity());

        model.addAttribute("cinemaHalls", MyCinemaHallsList);

        return "/movie_shows/edit-movie_show_next";
    }

    @PostMapping("/movie-show/edit-next")
    public String editMovieShowNext(@Valid MovieShow movieShow, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        CinemaHallEntity cinemaHallEntity = movieShowService.getHallRepo().
                findById(movieShow.getCinemaHallEntity().getId()).get();

        returnEntity.setCinemaHallEntity(cinemaHallEntity);
        BeanUtils.copyProperties(returnEntity, movieShowEntity, "id");
        movieShowService.getMovieShowRepo().save(movieShowEntity);

        return "redirect:/movie-show";
    }

}