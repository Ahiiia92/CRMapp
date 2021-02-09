package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contacts")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Contact {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstname, lastname, streetname, zipCode, city, email;
    private Contact_status contact_status;
    private LocalDateTime created_at;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() { super(); }

    public Contact(String firstname, String lastname, String streetname, String zipCode, String city, String email, Contact_status contact_status, LocalDateTime created_at, User user) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.streetname = streetname;
        this.zipCode = zipCode;
        this.city = city;
        this.email = email;
        this.contact_status = contact_status;
        this.created_at = created_at;
        this.user = user;
    }

    public Contact(String firstname, String lastName) {
        this.firstname = firstname;
        this.lastname = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact_status getContact_status() {
        return contact_status;
    }

    public void setContact_status(Contact_status contact_status) {
        this.contact_status = contact_status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
