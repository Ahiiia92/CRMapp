package com.crm.app.seeds;

import com.crm.app.models.Contact;
import com.crm.app.models.Contact_status;
import com.crm.app.models.Role;
import com.crm.app.models.User;
import com.crm.app.repositories.ContactRepository;
import com.crm.app.repositories.UserRepository;
import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Seeds implements CommandLineRunner {
    private ContactRepository contactRepository;
    private UserRepository userRepository;

    Faker faker = new Faker();

    public Seeds(UserRepository userRepository, ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Role
        Role admin = new Role();
        admin.setRoleName("Admin");

        // User
        System.out.println("Creating a fake User...");
        User eric = new User();
        eric.setEmail("e@e.fr");
        eric.setFirstname("Eric");
        eric.setLastname("Dujardin");
        eric.setUsername("eric");
        eric.setPassword("eric");
        eric.setRole(admin);
        userRepository.save(eric);
        System.out.println(eric.getFirstname() + " " + eric.getLastname() + " with user_id: " + eric.getId() + " has been created as " + eric.getRole());

        // Contact
        System.out.println("Creating fake contacts...");
        System.out.println("1...");
        Contact marou = new Contact(faker.name().firstName(), faker.name().lastName());
        marou.setCity(faker.address().city());
        marou.setZipCode(faker.address().zipCode());
        marou.setStreetname(faker.address().streetName());
        marou.setContact_status(Contact_status.LEAD);
        Optional<User> superAd = userRepository.findUserByUsername("superAdmin");
        marou.setUser(superAd.get());
        contactRepository.save(marou);
        System.out.println("Contact 1: " + marou.getFirstName() + " with contact_id: " + marou.getId() + " has been created!");
    }
}
