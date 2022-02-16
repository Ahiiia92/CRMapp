package com.crm.app.controllers;

import com.crm.app.models.User;
import com.crm.app.services.UserDetailsServiceDBImpl;
import com.crm.app.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserControllerTest {
    private User user;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsServiceDBImpl userDetailsServiceDBImpl;

    // We need our controller, we can inject the mocked service inside our ContactController
    @InjectMocks
    private UserController controller;

    @BeforeEach
    public void setup() {
        user = new User();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getUser() throws Exception {
        // Arrange: Create test data
        // Act:
        // Configure mock object to return the test data when the getUser() method of the UserService is invoked
        // Assert
        // invoke an HTTP GET request to the /api/v1/user/{id} URI
    }

    @Test
    public void Test_getAllUsers_ShouldReturnedAllExistingUsers() throws Exception {
        // Arrange: Create test data
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);
        // Act:
        // Configure mock object to return the test data when the getAll() method of the ContactService is invoked
       when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Assert:
        mockMvc.perform(get("http://localhost:8088/users"))
                .andExpect(status().isOk());
        verify(userService).getAllUsers();
    }

    @Test
    public void Test_getAllUsers_ReturnAJsonWithAllUsers() {
    }

    @Test
    public void Test_getUser_ReturnASingleUser() {}

}
