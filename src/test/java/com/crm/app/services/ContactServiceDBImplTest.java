package com.crm.app.services;

import com.crm.app.models.Contact;
import com.crm.app.repositories.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactServiceDBImplTest {

    // We need our service
    ContactServiceDBImpl service;
    // Need the depencency => the repo
    @Mock
    ContactRepository repo;

    @BeforeEach
    void setUp() {
        // Initialize our mocks
        MockitoAnnotations.openMocks(this);

        // create contact service
        service = new ContactServiceDBImpl();
        service.setContactRepository(repo);
    }

    @Test
    void getAllContacts() {
        // Create Data
        Contact contact = new Contact();
        List<Contact> returnedContact = new ArrayList<>();
        returnedContact.add(contact);
        // When the method is called on the repo, it should return the data we created above
        when(repo.findAll()).thenReturn(returnedContact);

        List<Contact> contacts = service.getAllContacts();

        assertEquals(contacts.size(), 1);
        // verify the interaction with the mocks: That the repo was called only once when .findAll is called.
        verify(repo, times(1)).findAll();
    }

    @Test
    void createContact() {
    }

    @Test
    void getContactById() {
    }

    @Test
    void updateContact() {
    }

    @Test
    void deleteContact() {
    }
}
