package com.web.BebusWebsite.controller;

import com.google.common.collect.Lists;
import com.web.BebusWebsite.entity.CinemaEntity;
import com.web.BebusWebsite.entity.MovieEntity;
import com.web.BebusWebsite.entity.MovieShowEntity;
import com.web.BebusWebsite.entity.StudioEntity;
import com.web.BebusWebsite.model.Cinema;
import com.web.BebusWebsite.model.Studio;
import com.web.BebusWebsite.service.GoogleChartsUtils;
import com.web.BebusWebsite.service.StudioService;
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
public class StudioController {
    @Autowired
    private GoogleChartsUtils gcu;

    @Autowired
    StudioService studioService;

    @RequestMapping("/studio")
    public String getStudio(Model model){

        Iterable<StudioEntity> studios = studioService.getStudioRepo().findAll();

        model.addAttribute("studios", studios);

        return "/studios/studio";
    }

    @RequestMapping("/studio/create")
    public String getCreateStudio(){
        return "/studios/create-studio";
    }

    @PostMapping("/studio/create")
    public String createStudio(@Valid Studio studio, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String createStudioCheck = studioService.createStudioCheck(studio);
        if (createStudioCheck != null){
            redirectAttributes.addFlashAttribute("wrongData", createStudioCheck);
            return "redirect:"+ referer;
        }

        StudioEntity studioEntity = new StudioEntity(studio.getName());
        studioService.getStudioRepo().save(studioEntity);

        return "redirect:/studio";
    }

    @RequestMapping("/studio/edit")
    public String getEditStudio(@Valid Studio studio, Model model) {

        model.addAttribute("id", studio.getId());
        model.addAttribute("name", studio.getName());

        return "/studios/edit-studio";
    }

    @PostMapping("/studio/edit")
    public String editStudio(@Valid Studio studio,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");
        String existStudioCheck = studioService.existenceStudioCheck(studio);
        if (existStudioCheck != null){
            redirectAttributes.addFlashAttribute("wrongData",existStudioCheck);
            return "redirect:"+ referer;
        }

        StudioEntity studioEntity = studioService.getStudioRepo().findById(studio.getId()).get();
        StudioEntity returnEntity = new StudioEntity(studio.getName());

        BeanUtils.copyProperties(returnEntity, studioEntity, "id");
        studioService.getStudioRepo().save(studioEntity);

        return "redirect:/studio";
    }

    @RequestMapping("/studio/delete")
    public String getDeleteStudio(@Valid Studio studio, Model model){

        model.addAttribute("id", studio.getId());
        model.addAttribute("name", studio.getName());

        return "/studios/delete-studio";
    }

    @Transactional
    @PostMapping("/studio/delete")
    public String deleteStudio(@Valid Studio studio, RedirectAttributes redirectAttributes){

        StudioEntity studioEntity = studioService.getStudioRepo().findById(studio.getId()).get();
        String name = studioEntity.getName();

        redirectAttributes.addFlashAttribute("deleted", "Студію \"" + name + "\" було видалено.");

        Iterable<MovieEntity> movieEntities = studioService.getMovieRepo().findAllByStudioEntityId(studio.getId());
        for (MovieEntity movieEntity : movieEntities) {
            studioService.getMovieShowRepo().deleteAllByMovieEntityId(movieEntity.getId());
        }
        studioService.getMovieRepo().deleteAllByStudioEntityId(studio.getId());
        studioService.getStudioRepo().deleteById(studio.getId());

        return "redirect:/studio";
    }

    @RequestMapping("/studio/details")
    public String getDetailsStudio(@Valid Studio studio, Model model) {

        Iterable<MovieEntity> movies = studioService.getMovieRepo().findAllByStudioEntityId(studio.getId());
        List<MovieShowEntity> movieShowEntityList = new ArrayList<>();
        for (MovieEntity movieEntity : movies) {
            List<MovieShowEntity> temp =  Lists.newArrayList(studioService.getMovieShowRepo().findAllByMovieEntityId(movieEntity.getId()));
            movieShowEntityList.addAll(temp);
        }

        List<CinemaEntity> cinemas = new ArrayList<>();
        for (MovieShowEntity movieShowEntity : movieShowEntityList) {
            cinemas.add(movieShowEntity.getCinemaEntity());
        }

        model.addAttribute("cinemasData", gcu.getStudioCinemasMap(cinemas));
        model.addAttribute("moviesData", gcu.getStudioMoviesMap(movies));
        model.addAttribute("name", studio.getName());
        model.addAttribute("movieShows", movieShowEntityList);

        return "/studios/details-studio";
    }
}
