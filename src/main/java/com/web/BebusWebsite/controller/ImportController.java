package com.web.BebusWebsite.controller;

import com.web.BebusWebsite.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class ImportController {
    @Autowired
    private ImportService importService;

    @PostMapping("/studio")
    public String uploadStudio(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importStudio(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/cinema")
    public String uploadCinema(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importCinema(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/hall")
    public String uploadCinemaHall(@RequestParam("file") MultipartFile reapExcelDataFile,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importCinemaHall(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/movie")
    public String uploadMovie(@RequestParam("file") MultipartFile reapExcelDataFile,
                             HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importMovie(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }

        return "redirect:" + referer;
    }

    @PostMapping("/movie-show")
    public String uploadMovieShow(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importMovieShow(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }

        return "redirect:" + referer;
    }
}
