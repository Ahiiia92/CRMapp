package com.crm.app.controllers;

import com.crm.app.exceptions.NoContactException;
import com.crm.app.models.Contact;
import com.crm.app.services.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Index
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok().body(contacts);
    }

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
    public ResponseEntity<Map<String, Boolean>> createContact(@Validated  @RequestBody Contact contact) {
        Contact contactCreated = contactService.createContact(contact);
        Map<String, Boolean> response = new HashMap<>();
        if (contactCreated == null) throw new NoContactException("The contact with id " + contact.getId() + "can't be found");

        response.put("Contact created", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
    }

    // Edit Contact
    // TODO: Mockito Test => Postman works with Admin + SuperAdmin
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Map<String, Boolean>> editContact(@Validated @PathVariable Integer id, @RequestBody Contact contactDetails) {
        Contact updatedContact = contactService.updateContact(id, contactDetails);
        Map<String, Boolean> response = new HashMap<>();
        if(updatedContact == null) {
            response.put("Contacted Updated", Boolean.FALSE);
            return ResponseEntity.noContent().build();
        }

        response.put("Contacted Updated", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
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
