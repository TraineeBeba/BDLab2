package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import com.web.BebusWebsite.entity.StudioEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.model.Movie;
import com.web.BebusWebsite.model.MovieShow;
import com.web.BebusWebsite.service.GoogleChartsUtils;
import com.web.BebusWebsite.service.StudioService;
import com.web.BebusWebsite.service.MovieService;
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
public class MovieController {
    @Autowired
    private GoogleChartsUtils gcu;

    @Autowired
    private MovieService movieService;

    @RequestMapping("/movie")
    public String getMovies(Model model, RedirectAttributes redirectAttributes){

        Iterable<MovieEntity> movies = movieService.getMovieRepo().findAll();

        model.addAttribute("movies", movies);

        return "/movies/movie";
    }

    @RequestMapping("/movie/create")
    public String getCreateMovie(Model model){
        Iterable<StudioEntity> studios = movieService.getStudioRepo().findAll();

        model.addAttribute("studios", studios);
        return "/movies/create-movie";
    }

    @PostMapping("/movie/create")
    public String createMovie(@Valid Movie movie, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");
        String createMovieCheck = movieService.createMovieCheck(movie);
        if (createMovieCheck != null){
            redirectAttributes.addFlashAttribute("wrongData", createMovieCheck);
            return "redirect:" + referer;
        }

        StudioEntity studioEntity = movieService.getStudioRepo().findById(movie.getStudioEntity().getId()).get();
        MovieEntity movieEntity = new MovieEntity(  movie.getName(),
                                                    movie.getVideoTrailerLink(),
                                                    movie.getDescription(),
                                                    studioEntity,
                                                    movie.getRating());
        movieService.getMovieRepo().save(movieEntity);

        return "redirect:/movie";
    }

    @RequestMapping("/movie/edit")
    public String getEditMovie(@Valid Movie movie, Model model) {
        Iterable<StudioEntity> studios = movieService.getStudioRepo().findAll();

        model.addAttribute("id", movie.getId());
        model.addAttribute("name", movie.getName());
        model.addAttribute("rating", movie.getRating());
        model.addAttribute("description", movie.getDescription());
        model.addAttribute("studio", movie.getStudioEntity());
        model.addAttribute("studios", studios);
        model.addAttribute("videoTrailerLink", movie.getVideoTrailerLink());

        return "/movies/edit-movie";
    }

    @PostMapping("/movie/edit")
    public String editMovie(@Valid Movie movie,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String existMovieCheck = movieService.existenceMovieCheck(movie);
        if (existMovieCheck != null){
            redirectAttributes.addFlashAttribute("wrongData",existMovieCheck);
            return "redirect:"+ referer;
        }

        System.out.println(movie.getStudioEntity());

        MovieEntity movieEntity = movieService.getMovieRepo().findById(movie.getId()).get();
        MovieEntity returnEntity = new MovieEntity( movie.getName(),
                                                    movie.getVideoTrailerLink(),
                                                    movie.getDescription(),
                                                    movie.getStudioEntity(),
                                                    movie.getRating());

        BeanUtils.copyProperties(returnEntity, movieEntity, "id");
        movieService.getMovieRepo().save(movieEntity);

        return "redirect:/movie";
    }

    @RequestMapping("/movie/delete")
    public String getDeleteMovie(@Valid Movie movie, Model model){

        model.addAttribute("id", movie.getId());
        model.addAttribute("name", movie.getName());
        model.addAttribute("rating", movie.getRating());
        model.addAttribute("description", movie.getDescription());
        model.addAttribute("studioName", movie.getStudioEntity().getName());
        model.addAttribute("trailer", movie.getVideoTrailerLink());

        return "/movies/delete-movie";
    }

    @Transactional
    @PostMapping("/movie/delete")
    public String deleteMovie(@Valid Movie movie, RedirectAttributes redirectAttributes){

        String filmName = movieService.getMovieRepo().findById(movie.getId()).get().getName();

        redirectAttributes.addFlashAttribute("deleted", "Фільм \"" + filmName + "\" було видалено.");
        movieService.getMovieShowRepo().deleteAllByMovieEntityId(movie.getId());
        movieService.getMovieRepo().deleteById(movie.getId());

        return "redirect:/movie";
    }

    @RequestMapping("/movie/details")
    public String getDetailsMovie(@Valid Movie movie, Model model) {

        Iterable<CinemaEntity> cinemas = movieService.getCinemaRepo().findAll();
        Iterable<MovieShowEntity> movieShows = movieService.getMovieShowRepo().findAllByMovieEntityId(movie.getId());

        model.addAttribute("cinemasData", gcu.getMovieCinemasMap(cinemas, movie));
        model.addAttribute("name", movie.getName());
        model.addAttribute("rating", movie.getRating());
        model.addAttribute("description", movie.getDescription());
        model.addAttribute("studio", movie.getStudioEntity().getName());
        model.addAttribute("trailer", movie.getVideoTrailerLink());
        model.addAttribute("movieShows", movieShows);

        return "/movies/details-movie";
    }
}