package com.crm.app.services;

import com.crm.app.models.Contact;
import com.crm.app.repositories.ContactRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExportServiceDBImplTest {

    List<Contact> contactList;
    ContactServiceDBImpl contactService;
    ExportServiceDBImpl exportService;

    @Mock
    ContactRepository contactRepo;

    @BeforeEach
    void setUp() {
        // Initialize our mocks
        MockitoAnnotations.openMocks(this);

        // Create a ContactList
        contactList = new ArrayList<>();
        contactList = contactRepo.findAll();

        // Create export service
        contactService = new ContactServiceDBImpl(contactRepo);
        exportService = new ExportServiceDBImpl(contactList);
    }

    @Test
    public void testCreateWorkbookAndCreateSheet() throws IOException {
        Workbook mockWorkbook = mock(Workbook.class);
        Sheet mockSheet = mock(Sheet.class);
        Row mockRow = mock(Row.class);
        Cell mockCell = mock(Cell.class);
        when(mockWorkbook.createSheet("Contacts")).thenReturn(mockSheet);
        when(mockSheet.createRow(0)).thenReturn(mockRow);
        when(mockSheet.createRow(anyInt())).thenReturn(mockRow);
        when(mockRow.createCell(anyInt())).thenReturn(mockCell);
    }
}
