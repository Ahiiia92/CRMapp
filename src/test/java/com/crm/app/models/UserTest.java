package com.crm.app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    // Create a property for User
    User user;
    Role roleValue;
    String firstnameValue, lastnameValue, emailValue, usernameValue, passwordValue;
    List<Contact> contactList;
    Set<Role> roles;

    // Before each test, we want to crete a new user
    @BeforeEach
    public void setUp() {
        user = new User();
        roleValue = new Role("testAdmin");
        roles = new HashSet<>();
        roles.add(roleValue);
        firstnameValue = "testFirstname";
        lastnameValue = "testLastname";
        emailValue = "testEmail@email.de";
        usernameValue = "testUsername";
        passwordValue = "testPassword";
        contactList = new ArrayList<>();
    }

    @Test
    void getRole() {
        user.setRoles(roles);
        assertEquals(roleValue, user.getRoles());
    }

    @Test
    void getId() {
        // Specific value we are testing:
        Integer idValue = 4;
        user.setId(idValue);
        assertEquals(idValue, user.getId());
    }

    @Test
    void getFirstname() {
        user.setFirstname(firstnameValue);
        assertEquals(firstnameValue, user.getFirstname());
    }

    @Test
    void getLastname() {
        user.setLastname(lastnameValue);
        assertEquals(lastnameValue, user.getLastname());
    }

    @Test
    void getEmail() {
        user.setEmail(emailValue);
        assertEquals(emailValue, user.getEmail());
    }

    @Test
    void getUsername() {
        user.setUsername(usernameValue);
        assertEquals(usernameValue, user.getUsername());
    }

    @Test
    void getPassword() {
        user.setPassword(passwordValue);
        assertEquals(passwordValue, user.getPassword());
    }

    @Test
    void getContacts() {
        user.setContacts(contactList);
        assertEquals(contactList.size(), user.getContacts().size());
    }
}
