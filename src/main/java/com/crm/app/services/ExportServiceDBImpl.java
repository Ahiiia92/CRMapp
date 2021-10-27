package com.crm.app.services;

import com.crm.app.models.Contact;
import javassist.bytecode.ByteArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExportServiceDBImpl implements ExportService {
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private List<Contact> listContacts;
    // link to create this Excel Sheet: https://www.codejava.net/frameworks/spring-boot/export-data-to-excel-example
    // link to create this Excel Sheet: https://www.freecodecamp.org/news/generate-excel-report-in-spring-rest-api/


    public ExportServiceDBImpl(List<Contact> listContacts) {
        this.listContacts = listContacts;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        generateReport(response);
    }

    public void generateReport(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    // Private Methods

    private void writeHeaderLine() {
        Sheet sheet = workbook.createSheet("Contacts");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        // Create the header cells:
        createCell(row, 0, "Contact ID", style);
        createCell(row, 1, "Firstname", style);
        createCell(row, 2, "Lastname", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Phone", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Contact contact : listContacts) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, contact.getId(), style);
            createCell(row, columnCount++, contact.getLastname(), style);
            createCell(row, columnCount++, contact.getFirstname(), style);
            createCell(row, columnCount++, contact.getEmail(), style);
            createCell(row, columnCount++, contact.getPhone(), style);
        }
    }
}
