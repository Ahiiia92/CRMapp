package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Contacts")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Contact {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstname, lastname, address, email, profession;

    @Enumerated(value = EnumType.STRING)
    private Contact_status contact_status;

    // A contact is either a buyer or a seller
    private Boolean buyer;
    private LocalDateTime created_at;
    private Float lng, lat;

    // From Recipe to Comments : Recipe have many comments while this comment will have just one Recipe
    // Unique set of comments
    @JsonManagedReference // to avoid a loop effect inside our object, we need to defined both references
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "contact") // with mappedBy = a property in comment will be called recipe
    private Set<Comment> comments = new HashSet<>();


    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() { super(); }

    public Contact(String firstname, String lastname, String address, String email, Contact_status contact_status, User user, String profession) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.contact_status = contact_status;
        this.user = user;
        this.profession = profession;
        this.created_at = LocalDateTime.now();
    }

    public Contact(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", profession='" + profession + '\'' +
                ", contact_status=" + contact_status +
                ", buyer=" + buyer +
                ", created_at=" + created_at +
                ", lng=" + lng +
                ", lat=" + lat +
                ", comments=" + comments +
                ", user=" + user +
                '}';
    }
}
