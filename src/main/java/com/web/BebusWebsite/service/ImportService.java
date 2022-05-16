package com.web.BebusWebsite.service;

import com.web.BebusWebsite.entity.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImportService {
    private final int CINEMA_COLUMNS = 3;
    private final int MOVIE_SHOW_COLUMNS = 9;
    private final int MOVIE_COLUMNS = 6;
    private final int HALL_COLUMNS = 5;
    private final int STUDIO_COLUMNS = 2;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieShowService movieShowService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaHallService cinemaHallService;

    @Autowired
    private StudioService studioService;

    private String checkEmpty(MultipartFile reapExcelDataFile){
        if (reapExcelDataFile.isEmpty()){
            return "Файл порожній!";
        }
        return null;
    }
    private String checkCells(int numberOfCells, int needed){
        if (numberOfCells != needed){
            return "Неправильна кількість колонок!";
        }
        return null;
    }

    public String importStudio(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, STUDIO_COLUMNS) != null){
            return checkCells(numberOfCells, STUDIO_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            StudioEntity studioEntity = new StudioEntity();

            XSSFRow row = worksheet.getRow(i);

            studioEntity.setName(row.getCell(1).getStringCellValue());

            String check = studioService.createStudioCheck(studioEntity);
            if (check != null){
                return check;
            } else {
                studioService.getStudioRepo().save(studioEntity);
            }

        }

        return null;
    }

    public String importCinema(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, CINEMA_COLUMNS) != null){
            return checkCells(numberOfCells, CINEMA_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            CinemaEntity cinemaEntity = new CinemaEntity();

            XSSFRow row = worksheet.getRow(i);

            cinemaEntity.setName(row.getCell(1).getStringCellValue());
            cinemaEntity.setAddress(row.getCell(2).getStringCellValue());

            String check = cinemaService.createCinemaCheck(cinemaEntity);
            if (check != null){
                return check;
            } else {
                cinemaService.getCinemaRepo().save(cinemaEntity);
            }

        }

        return null;
    }

    public String importCinemaHall(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, HALL_COLUMNS) != null){
            return checkCells(numberOfCells, HALL_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            CinemaHallEntity cinemaHallEntity = new CinemaHallEntity();

            XSSFRow row = worksheet.getRow(i);

            CinemaEntity cinemaEntity = cinemaService.getCinemaRepo().findByAddress(row.getCell(3).getStringCellValue());

            cinemaHallEntity.setNumber((int)(row.getCell(1).getNumericCellValue()));
            cinemaHallEntity.setCinemaEntity(cinemaEntity);
            cinemaHallEntity.setPlacesCount((int)(row.getCell(4).getNumericCellValue()));

            String checkHall = cinemaHallService.createHallCheck(cinemaHallEntity);
            if (checkHall != null){
                return checkHall;
            } else {
                cinemaHallService.getCinemaHallRepo().save(cinemaHallEntity);
            }

        }

        return null;
    }

    public String importMovie(MultipartFile reapExcelDataFile) throws IOException {
        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, MOVIE_COLUMNS) != null){
            return checkCells(numberOfCells, MOVIE_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            MovieEntity movieEntity = new MovieEntity();

            XSSFRow row = worksheet.getRow(i);

            StudioEntity studioEntity = studioService.getStudioRepo().findByName(row.getCell(4).getStringCellValue());


            movieEntity.setName(row.getCell(1).getStringCellValue());
            movieEntity.setRating((int)(row.getCell(2).getNumericCellValue()));
            movieEntity.setDescription(row.getCell(3).getStringCellValue());
            movieEntity.setStudioEntity(studioEntity);
            movieEntity.setVideoTrailerLink(row.getCell(5).getStringCellValue());

            String checkMovie = movieService.createMovieCheck(movieEntity);
            if (checkMovie != null){
                return checkMovie;
            } else {
                movieService.getMovieRepo().save(movieEntity);
            }

        }

        return null;
    }

    public String importMovieShow(MultipartFile reapExcelDataFile) throws IOException {
        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, MOVIE_SHOW_COLUMNS) != null){
            return checkCells(numberOfCells, MOVIE_SHOW_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            MovieShowEntity movieShowEntity = new MovieShowEntity();

            XSSFRow row = worksheet.getRow(i);

            MovieEntity movieEntity = movieService.getMovieRepo().findByName(row.getCell(1).getStringCellValue());
            CinemaEntity cinemaEntity = cinemaService.getCinemaRepo().findByAddress(row.getCell(3).getStringCellValue());
            CinemaHallEntity cinemaHallEntity = cinemaHallService.getCinemaHallRepo().findCinemaHallEntityByCinemaEntityAndNumber(cinemaEntity, (int) row.getCell(6).getNumericCellValue());


            movieShowEntity.setLink(row.getCell(5).getStringCellValue());
            movieShowEntity.setMovieEntity(movieEntity);
            movieShowEntity.setCinemaEntity(cinemaEntity);
            movieShowEntity.setCinemaHallEntity(cinemaHallEntity);
            movieShowEntity.setMinPrice((int)row.getCell(8).getNumericCellValue());

            String checkMovie = movieShowService.createMovieShowCheck(movieShowEntity);
            if (checkMovie != null){
                return checkMovie;
            } else {
                movieShowService.getMovieShowRepo().save(movieShowEntity);
            }

        }

        return null;
    }
}
