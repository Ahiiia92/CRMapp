package com.crm.app.services;

import com.crm.app.models.Contact;
import javassist.bytecode.ByteArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExportServiceDBImpl implements ExportService {
    private XSSFSheet sheet;
    private XSSFWorkbook wb;
    private List<Contact> listContacts;
    // link to create this Excel Sheet: https://www.codejava.net/frameworks/spring-boot/export-data-to-excel-example
    // link to create this Excel Sheet: https://www.freecodecamp.org/news/generate-excel-report-in-spring-rest-api/

    @Override
    public ByteArray generateXlsxReport() {
        wb = new XSSFWorkbook();
        return generateReport(wb);
    }

    @Override
    public ByteArray generateXlsReport() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        return generateReport(wb);
    }

    private ByteArray generateReport(Workbook wb) throws IOException {
        writeHeaderLine(wb);
        writeRows(wb);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wb.write(out);

        out.close();
        wb.close();
        return out.toByteArray();
    }

    private void writeHeaderLine(Workbook wb) {
        Sheet sheet = wb.createSheet("Contacts");
        Row row = sheet.createRow(0);

        CellStyle style = wb.createCellStyle();
        XSSFFont font = (XSSFFont) wb.createFont();
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

    private void createCell(Row row, int colunmCount, String contact_id, CellStyle style) {
        sheet.autoSizeColumn(colunmCount);
    }

    private void writeRows(Workbook wb) {
    }
}
