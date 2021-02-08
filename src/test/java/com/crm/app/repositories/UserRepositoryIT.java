package com.crm.app.repositories;

import com.crm.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// JUnit 4 needs RunWith
// @RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findUserByUsername() {
//        Optional<User> userOptional = userRepository.findUserByUsername("eric");
//        assertEquals("eric", userOptional.get().getUsername());
    }
}
