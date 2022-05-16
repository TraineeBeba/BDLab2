package com.web.BebusWebsite.export;

import com.web.BebusWebsite.entity.MovieShowEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MovieShowDataExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<MovieShowEntity> movieShowEntityList;

    public MovieShowDataExcelExporter(List<MovieShowEntity> movieShowEntityList) {
        this.movieShowEntityList = movieShowEntityList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("MovieShows");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Movie", style);
        createCell(row, 2, "CINEMA", style);
        createCell(row, 3, "CINEMA_ADDRESS", style);
        createCell(row, 4, "RATING", style);
        createCell(row, 5, "TRAILER", style);
        createCell(row, 6, "HALL", style);
        createCell(row, 7, "PLACES_COUNT", style);
        createCell(row, 8, "MIN_PRICE", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (MovieShowEntity movieShowEntity : movieShowEntityList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, movieShowEntity.getId(), style);
            createCell(row, columnCount++, movieShowEntity.getMovieEntity().getName(), style);
            createCell(row, columnCount++, movieShowEntity.getCinemaEntity().getName(), style);
            createCell(row, columnCount++, movieShowEntity.getCinemaEntity().getAddress(), style);
            createCell(row, columnCount++, movieShowEntity.getMovieEntity().getRating(), style);
            createCell(row, columnCount++, movieShowEntity.getMovieEntity().getVideoTrailerLink(), style);
            createCell(row, columnCount++, movieShowEntity.getCinemaHallEntity().getNumber(), style);
            createCell(row, columnCount++, movieShowEntity.getCinemaHallEntity().getPlacesCount(), style);
            createCell(row, columnCount++, movieShowEntity.getMinPrice(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
