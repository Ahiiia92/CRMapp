package com.crm.app.seeds;

import com.crm.app.models.*;
import com.crm.app.repositories.*;
import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class Seeds implements CommandLineRunner {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final NoteRepository noteRepository;
    private final ViewingRepository viewingRepository;

    Faker faker = new Faker();

    public Seeds(UserRepository userRepository,
                 @Qualifier("contactRepository") ContactRepository contactRepository,
                 PropertyRepository propertyRepository,
                 @Qualifier("noteRepository") NoteRepository noteRepository,
                 @Qualifier("viewingRepository") ViewingRepository viewingRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.noteRepository = noteRepository;
        this.viewingRepository = viewingRepository;
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
        u1.setEmail(faker.internet().emailAddress());
        u1.setOwner(true);
        u1.setAmbassador(true);
        u1.setChildren(faker.number().numberBetween(0,5));
        u1.setProfession("Accountant");
        u1.setPhone(faker.phoneNumber().phoneNumber());
        u1.setSellingProject(true);
        u1.setOwnerSince(LocalDate.of(1997, 04,15));
        u1.setCreated_at();
        u1.setContact_status(Contact_status.SEEN);
        Optional<User> superAd = userRepository.findUserByUsername("superAdmin");
        u1.setUser(superAd.get());
        contactRepository.save(u1);
        System.out.println("Contact 1: " + u1.getFirstname() + " with contact_id: " + u1.getId() + " has been created!");
        System.out.println(u1.toString());

        System.out.println("2...");
        Contact u2 = new Contact(faker.name().firstName(), faker.name().lastName());
        u2.setAddress(faker.address().fullAddress());
        u2.setContact_status(Contact_status.OPPORTUNITY);
        Optional<User> admin1 = userRepository.findUserByUsername("admin");
        u2.setUser(admin1.get());
        contactRepository.save(u2);
        System.out.println("Contact 2: " + u2.getFirstname() + " with contact_id: " + u2.getId() + " has been created!");

        // Property
        System.out.println("Creating some properties...");
        System.out.println("Prop 1...");
        Property p1 = new Property(
                faker.company().name(),
                faker.number().numberBetween(1,6),
                faker.number().randomDouble(2,150_000, 2_500_000),
                faker.number().randomDouble(1, 25, 350),
                "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1650&q=80"
        );
        p1.setContact(u1);
        p1.setUser(eric);
        p1.setAddress(faker.address().fullAddress());
        propertyRepository.save(p1);
        System.out.println("Property 1: " + p1.getTitle() + " with contact " + p1.getContact().getFirstname() + "and User: " + p1.getUser().getFirstname() + " has been added.");

        System.out.println("Prop 2...");
        Property p2 = new Property(
                faker.company().name(),
                faker.number().numberBetween(1,6),
                faker.number().randomDouble(2,150_000, 2_500_000),
                faker.number().randomDouble(1, 25, 350),
                "https://images.unsplash.com/photo-1448630360428-65456885c650?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1347&q=80"
        );
        p2.setContact(u2);
        p2.setUser(eric);
        p2.setAddress(faker.address().fullAddress());
        propertyRepository.save(p2);
        System.out.println("Property 2: " + p2.getTitle() + " (" + p2.getId() + ") with contact " + p2.getContact().getFirstname() + "and User: " + p2.getUser().getFirstname() + " has been added.");

        // Notes
        System.out.println("Creating some notes...");
        Set<Note> notes = new HashSet<>();
        Note no1 = new Note();
        notes.add(no1);
        no1.setContent(faker.howIMetYourMother().quote());
        no1.setContact(u1);
        no1.setCreationDate();
        no1.setDueDate(LocalDateTime.of(2021, 6, 21, 14, 30));
        noteRepository.save(no1);
        u1.setNotes(notes);
        contactRepository.save(u1);
        System.out.println("Note 1: " + no1.getContent() + " with Contact: " + u1.getFirstname());
        System.out.println(u1.toString());

        // Viewings
        System.out.println("Creating some viewings");
        Viewing viewing1 = new Viewing(
                LocalDateTime.of(2021, Month.JULY, 25, 15, 30),
                notes
                );
        System.out.println(viewing1);
        viewingRepository.save(viewing1);
        System.out.println("Viewing 1: " + viewing1.getNotes() + " with date: " + viewing1.getViewingDate());
    }
}
