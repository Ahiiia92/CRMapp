package com.crm.app.controllers;

import com.crm.app.exceptions.NoContactException;
import com.crm.app.models.Contact;
import com.crm.app.models.User;
import com.crm.app.services.ContactService;
import com.crm.app.services.ExportService;
import com.crm.app.services.UserService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Contacts.",
        tags = {"contact"})
@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class ContactController {
    private final ContactService contactService;
    private final ExportService exportService;
    private final UserService userService;

    public ContactController(ContactService contactService, ExportService exportService, UserService userService) {
        this.contactService = contactService;
        this.exportService = exportService;
        this.userService = userService;
    }

    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @ApiOperation(value = "Retrieving all contacts", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response=List.class )  })
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts() {
        User user = userService.getCurrentUser();
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok().body(contacts);
    }


    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @ApiOperation(value = "Find contact by ID", notes = "Returns a single contact", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response=Contact.class),
            @ApiResponse(code = 404, message = "Contact not found") })
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> showcontact(
            @ApiParam("Id of the contact to be obtained. Cannot be empty.")
            @PathVariable Integer id) {
        User user = userService.getCurrentUser();

        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok().body(contact);
    }


    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @ApiOperation(value = "Add a new contact", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contact created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Contact already exists") })
    @PostMapping("/contacts")
    public ResponseEntity<Map<String, Boolean>> createContact(
            @ApiParam("Contact to add. Cannot null or empty.")
            @Validated  @RequestBody Contact contact) {
        User user = userService.getCurrentUser();

        Contact contactCreated = contactService.createContact(contact);
        Map<String, Boolean> response = new HashMap<>();
        if (contactCreated == null) throw new NoContactException("The contact with id " + contact.getId() + "can't be found");

        response.put("Contact created", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
    }


    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @ApiOperation(value = "Update an existing contact", tags = {"contacts"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Contact not found"),
            @ApiResponse(code = 405, message = "Validation exception")
    })
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Map<String, Boolean>> editContact(
            @ApiParam("Id of the contact to be updated. Cannot be empty.")
            @PathVariable Integer id,
            @ApiParam("Contact to update. Cannot null or empty.")
            @Validated @RequestBody Contact contactDetails) {
        User user = userService.getCurrentUser();

        Contact updatedContact = contactService.updateContact(id, contactDetails);
        Map<String, Boolean> response = new HashMap<>();
        if(updatedContact == null) {
            response.put("Contact Updated", Boolean.FALSE);
            return ResponseEntity.noContent().build();
        }

        response.put("Contacted Updated", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
    }


    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @ApiOperation(value = "Deletes a contact", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 404, message = "Contact not found") })
    @DeleteMapping("/contacts/{id}")
    public Map<String, Boolean> deleteContact(
            @ApiParam("Id of the contact to be deleted. Cannot be empty.")
            @PathVariable Integer id) {
        User user = userService.getCurrentUser();

        contactService.deleteContact(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // TODO: Mockito Test + Export
    @GetMapping("/contacts/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contacts_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Contact> listContacts = contactService.getAllContacts();
        exportService.export(response, listContacts);
    }
}
