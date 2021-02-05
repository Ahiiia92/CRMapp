package com.crm.app.controllers;

import com.crm.app.models.Contact;
import com.crm.app.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/")
public class ContactController {
    @Autowired
    private ContactService contactService;

    // Index
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @GetMapping("/contacts")
    public List<Contact> getContacts() { return contactService.getAllContacts(); }

    // Show a specific Contact
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> showcontact(@PathVariable Integer id) {
        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok().body(contact);
    }

    // Add Contacts
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @PostMapping("/contacts")
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.createContact(contact);
    }

    // Edit Contact
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> editContact(@PathVariable Integer id, @RequestBody Contact contactDetails) {
        Contact updatedContact = contactService.updateContact(id, contactDetails);
        return ResponseEntity.ok(updatedContact);
    }

    // Delete Contact
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @DeleteMapping("/contacts/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable Integer id) {
        contactService.deleteContact(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
