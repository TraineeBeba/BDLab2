package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.entity.*;
import com.web.BebusWebsite.export.*;
import com.web.BebusWebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExportController {
    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaHallService cinemaHallService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieShowService movieShowService;

    @Autowired
    private StudioService studioService;

    @RequestMapping("/export/cinema")
    public void getBrandData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CinemasData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<CinemaEntity> cinemaEntities = cinemaService.getCinemaRepo().findAll();
        List<CinemaEntity> cinemaEntityList = new ArrayList<>();
        for (CinemaEntity cinemaEntity : cinemaEntities){
            cinemaEntityList.add(cinemaEntity);
        }
        CinemaDataExcelExporter excelExporter = new CinemaDataExcelExporter(cinemaEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/studio")
    public void getCategoriesData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=StudiosData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<StudioEntity> studioEntities = studioService.getStudioRepo().findAll();
        List<StudioEntity> studioEntityList = new ArrayList<>();
        for (StudioEntity studioEntity : studioEntities){
            studioEntityList.add(studioEntity);
        }
        StudioDataExcelExporter excelExporter = new StudioDataExcelExporter(studioEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/hall")
    public void getCinemaHallData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=HallsData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<CinemaHallEntity> cinemaHallEntities = cinemaHallService.getCinemaHallRepo().findAll();
        List<CinemaHallEntity> cinemaHallEntityList = new ArrayList<>();
        for (CinemaHallEntity cinemaHallEntity : cinemaHallEntities){
            cinemaHallEntityList.add(cinemaHallEntity);
        }
        CinemaHallDataExcelExporter excelExporter = new CinemaHallDataExcelExporter(cinemaHallEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/movie-show")
    public void getMovieShowData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MovieShowData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<MovieShowEntity> movieShowEntities = movieShowService.getMovieShowRepo().findAll();
        List<MovieShowEntity> movieShowEntityList = new ArrayList<>();
        for (MovieShowEntity cinemaHallEntity : movieShowEntities){
            movieShowEntityList.add(cinemaHallEntity);
        }
        MovieShowDataExcelExporter excelExporter = new MovieShowDataExcelExporter(movieShowEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/movie")
    public void getMovieData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MovieData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<MovieEntity> movieEntities = movieService.getMovieRepo().findAll();
        List<MovieEntity> movieShowEntityList = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntities){
            movieShowEntityList.add(movieEntity);
        }
        MovieDataExcelExporter excelExporter = new MovieDataExcelExporter(movieShowEntityList);
        excelExporter.export(response);
    }
}
