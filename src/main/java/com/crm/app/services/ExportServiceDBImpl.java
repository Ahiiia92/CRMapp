package com.crm.app.services;

import com.crm.app.models.Contact;
import com.crm.app.repositories.ContactRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    public void export(HttpServletResponse response, List<Contact> listContacts) throws IOException {
        generateReport(response, listContacts);
    }

    public void generateReport(HttpServletResponse response, List<Contact> listContacts) throws IOException {
        writeHeaderLine();
        writeDataLines(listContacts);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    // Private Methods

    private void writeHeaderLine() {
        System.out.println("I'm in writeHeaderLine");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());
        sheet = workbook.createSheet("Contacts" + currentDateTime);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        System.out.println("Creating the titles");

        // Create the header cells:
        createCell(row, 0, "Contact ID", style);
        createCell(row, 1, "Firstname", style);
        createCell(row, 2, "Lastname", style);
        createCell(row, 3, "Address", style);
        createCell(row, 4, "Profession", style);
        createCell(row, 5, "Email", style);
        createCell(row, 6, "Phone", style);
        createCell(row, 7, "Selling Project?", style);
        createCell(row, 8, "Social Media", style);
        createCell(row, 9, "Ambassador?", style);
        createCell(row, 10, "Children", style);
        createCell(row, 11, "Status", style);
        createCell(row, 12, "Doorbell", style);
        createCell(row, 13, "Next Viewing Date", style);
        createCell(row, 14, "Owner", style);
        createCell(row, 15, "Owner Since", style);
        createCell(row, 16, "User", style);
        createCell(row, 17, "Created at", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof EnumType) {
            cell.setCellValue(value.toString());
        } else if (value instanceof LocalDate) {
            cell.setCellValue(String.valueOf(value));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(List<Contact> listContacts) {
        System.out.println("I'm in writeDataLines");
        int rowCount = 1;

        System.out.println("Setting the style");
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        System.out.println("Inside the listContacts: " + listContacts);
        System.out.println("Starting the iteration: ");
        for (Contact contact : listContacts) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            System.out.println("ColumnCount now: " + columnCount);
            System.out.println("Creating the first cell of data in " + row);
            createCell(row, columnCount++, contact.getId(), style);
            System.out.println("First cell in DataLines done: " + contact.getLastname());
            createCell(row, columnCount++, contact.getLastname(), style);
            createCell(row, columnCount++, contact.getFirstname(), style);
            createCell(row, columnCount++, contact.getAddress(), style);
            createCell(row, columnCount++, contact.getProfession(), style);
            createCell(row, columnCount++, contact.getEmail(), style);
            createCell(row, columnCount++, contact.getPhone(), style);
            createCell(row, columnCount++, contact.getSellingProject(), style);
            createCell(row, columnCount++, contact.getSocialMedia().toString(), style);
            createCell(row, columnCount++, contact.getAmbassador(), style);
            createCell(row, columnCount++, contact.getChildren(), style);
            createCell(row, columnCount++, contact.getContact_status().toString(), style);
            createCell(row, columnCount++, contact.getDoorBell(), style);
            createCell(row, columnCount++, contact.getNextViewingDate() == null ? "" : contact.getNextViewingDate().format(DateTimeFormatter.ofPattern("dd-MM-uuuu'T'HH:mm")) , style);
            createCell(row, columnCount++, contact.getOwner(), style);
            createCell(row, columnCount++, contact.getOwnerSince(), style);
            createCell(row, columnCount++, contact.getUser().getUsername(), style);
            createCell(row, columnCount++, contact.getCreated_at() == null ? "" : contact.getCreated_at().toString(), style);
            System.out.println("Row done");
        }
    }
}
