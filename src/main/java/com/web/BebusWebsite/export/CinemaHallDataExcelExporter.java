package com.web.BebusWebsite.export;

import com.web.BebusWebsite.entity.CinemaHallEntity;
import com.web.BebusWebsite.entity.StudioEntity;
import com.web.BebusWebsite.model.CinemaHall;
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

public class CinemaHallDataExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CinemaHallEntity> cinemaHallEntities;

    public CinemaHallDataExcelExporter(List<CinemaHallEntity> cinemaHallEntities) {
        this.cinemaHallEntities = cinemaHallEntities;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Halls");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "NUMBER", style);
        createCell(row, 2, "CINEMA_NAME", style);
        createCell(row, 3, "CINEMA_ADDRESS", style);
        createCell(row, 4, "PLACES_COUNT", style);

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

        for (CinemaHallEntity cinemaHallEntity : cinemaHallEntities) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, cinemaHallEntity.getId(), style);
            createCell(row, columnCount++, cinemaHallEntity.getNumber(), style);
            createCell(row, columnCount++, cinemaHallEntity.getCinemaEntity().getName(), style);
            createCell(row, columnCount++, cinemaHallEntity.getCinemaEntity().getAddress(), style);
            createCell(row, columnCount++, cinemaHallEntity.getPlacesCount(), style);
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
