package com.crm.app.seeds;

import com.crm.app.models.*;
import com.crm.app.repositories.ContactRepository;
import com.crm.app.repositories.PropertyRepository;
import com.crm.app.repositories.UserRepository;
import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Seeds implements CommandLineRunner {
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private PropertyRepository propertyRepository;

    Faker faker = new Faker();

    public Seeds(UserRepository userRepository, ContactRepository contactRepository, PropertyRepository propertyRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
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
        eric.setPassword("eric123");
        eric.setRole(admin);
        userRepository.save(eric);
        System.out.println(eric.getFirstname() + " " + eric.getLastname() + " with user_id: " + eric.getId() + " has been created as " + eric.getRole());

        // Contact
        System.out.println("Creating fake contacts...");
        System.out.println("1...");
        Contact u1 = new Contact(faker.name().firstName(), faker.name().lastName());
        u1.setAddress(faker.address().fullAddress());
        u1.setContact_status(Contact_status.LEAD);
        Optional<User> superAd = userRepository.findUserByUsername("superAdmin");
        u1.setUser(superAd.get());
        contactRepository.save(u1);
        System.out.println("Contact 1: " + u1.getFirstname() + " with contact_id: " + u1.getId() + " has been created!");

        System.out.println("2...");
        Contact u2 = new Contact(faker.name().firstName(), faker.name().lastName());
        u2.setAddress(faker.address().fullAddress());
        u2.setContact_status(Contact_status.OPPORTUNITY);
        Optional<User> admin1 = userRepository.findUserByUsername("admin");
        u2.setUser(admin1.get());
        contactRepository.save(u2);
        System.out.println("Contact é: " + u2.getFirstname() + " with contact_id: " + u2.getId() + " has been created!");
        System.out.println("Creating some properties...");


        // Property
        System.out.println("Creating some properties...");
        Property p1 = new Property(faker.company().name(), faker.number().numberBetween(1,6), faker.number().randomDouble(2,150_000, 2_500_000), faker.number().randomDouble(1, 25, 350));
        p1.setContact(u1);
        p1.setUser(eric);
        propertyRepository.save(p1);
        System.out.println("Property 1: " + p1.getTitle() + " with contact " + p1.getContact().getFirstname() + "and User: " + p1.getUser().getFirstname() + " has been added.");
    }
}
