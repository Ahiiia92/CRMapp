package com.crm.app.controllers;

import com.crm.app.models.Contact;
import com.crm.app.models.User;
import com.crm.app.repositories.ContactRepository;
import com.crm.app.services.ContactService;
import org.apache.catalina.filters.CorsFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ContactControllerTest {
    private MockMvc mockMvc;
    private User user;

    //  we can return mocked data when we call a method from this service
    @Mock
    private ContactService contactService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private Authentication auth;

    // We need our controller, we can inject the mocked service inside our ContactController
    @InjectMocks
    private ContactController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // MockMvc is initialized using the MockMvcBuilders#standaloneSetup(...).build() method. Optionally we can add filters, interceptors or etc. using the .addFilter() or .addInterceptor() methods.
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        // Mock Security Feature : Current User : Buyer
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
//        Mockito.when(auth.getName()).thenReturn("testBuyerName");
//        Mockito.when(userDetailsService.findUserByUsername("testBuyerName")).thenReturn(user);
    }

    @Test
    @WithMockUser
    void getContacts() throws Exception {
        // Create test data which’ll be returned as a response in the rest service.
        List<Contact> contacts = new ArrayList<>();
        Contact c1 = new Contact();
        c1.setFirstname("testFirstname1");
        Contact c2 = new Contact();
        c2.setFirstname("testFirstname2");
        contacts.add(c1);
        contacts.add(c2);

        // Configure mock object to return the test data when the getAll() method of the ContactService is invoked
        when(contactService.getAllContacts()).thenReturn(contacts);

        // invoke an HTTP GET request to the /api/v1/contacts URI
        mockMvc.perform(get("http://localhost:8088/api/v1/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].firstname", is("testFirstname1")))
        .andExpect(jsonPath("$[1].firstname", is("testFirstname2")));
        verify(contactService, times(1)).getAllContacts();
        verifyNoMoreInteractions(contactService);
    }

    @Test
    void showcontact() {
    }

    @Test
    void createContact() {
    }

    @Test
    void editContact() {
    }

    @Test
    void deleteContact() {
    }
}
