package com.andisramja.studentmanagement.excel;

import com.andisramja.studentmanagement.dto.StudentDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Shërbimi që eksporton listën e studentëve në një file Excel (.xlsx)
 * duke përdorur librarinë Apache POI (workbook i tipit XSSF).
 * <p>
 * Rreshti i parë përmban titujt me shkronja të trasha mbi sfond me ngjyrë,
 * ndërsa kolonat ripërmasohen automatikisht sipas përmbajtjes.
 * </p>
 *
 * @author Andis Ramja
 */
@Service
public class ExcelExportService {

    /** Titujt e kolonave të file-it Excel. */
    private static final String[] HEADERS = {"ID", "Emri", "Mbiemri", "Email", "Datëlindja", "GPA", "Dega"};

    /** Emri i fletës (sheet) brenda workbook-ut. */
    private static final String SHEET_NAME = "Studentët";

    /** Formati i datave i përdorur në file-in Excel. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Gjeneron një workbook XSSF me listën e studentëve dhe e kthen si varg bajtesh.
     *
     * @param students lista e studentëve që do të eksportohet
     * @return përmbajtja binare e file-it .xlsx
     */
    public byte[] exportStudentsToExcel(List<StudentDTO> students) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // Build the bold, colored style used for the header row
            CellStyle headerStyle = createHeaderStyle(workbook);

            // Write the header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            // Write a data row for each student
            int rowIndex = 1;
            if (students != null) {
                for (StudentDTO student : students) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(student.getId() != null ? student.getId() : 0);
                    row.createCell(1).setCellValue(student.getFirstName());
                    row.createCell(2).setCellValue(student.getLastName());
                    row.createCell(3).setCellValue(student.getEmail());
                    row.createCell(4).setCellValue(
                            student.getDateOfBirth() != null ? student.getDateOfBirth().format(DATE_FORMATTER) : "");
                    row.createCell(5).setCellValue(student.getGpa() != null ? student.getGpa() : 0.0);
                    row.createCell(6).setCellValue(student.getMajor());
                }
            }

            // Resize each column so the content fits nicely
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Gabim gjatë gjenerimit të file-it Excel", e);
        }
    }

    /**
     * Krijon stilin e rreshtit të titujve: shkronja të trasha të bardha
     * mbi një sfond blu të errët.
     *
     * @param workbook workbook-u aktual ku regjistrohet stili
     * @return stili i konfiguruar për qelizat e titujve
     */
    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerStyle;
    }
}
