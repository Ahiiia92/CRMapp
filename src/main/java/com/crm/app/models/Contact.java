package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.javafaker.DateAndTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "Class representing a contact in the application.")
@Entity
@Table(name = "Contacts")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Contact {
    @ApiModelProperty(notes = "Unique identifier of the Contact.",
            example = "1", required = true, position = 0)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "Firstname and lastname of the contact.",
            example = "Jessica Abigail", required = true, position = 1)
    @Min(value = 3, message = "Too short entry")
    @NotNull
    private String firstname, lastname, profession, phone;

    @ApiModelProperty(notes = "Full address of the contact",
            example = "221B Backer Street NW1 6XE London",
            required = true,
            position = 3)
    @NotNull(message = "Can't be empty")
    private String address;

    @ApiModelProperty(notes = "")
    private String website;

    @Email
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Contact_status contact_status;

    // A contact is either a buyer or a seller
    private Boolean sellingProject, owner, ambassador;
    private LocalDateTime created_at;
    // How many children have our contact
    private Integer children;
    private LocalDateTime ownerSince;

    // From Contact to Comments : Contact have many comments while this comment will have just one Contact
    // Unique set of comments
    @JsonManagedReference // to avoid a loop effect inside our object, we need to defined both references
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "contact") // with mappedBy = a property in comment will be called contact
    private Set<Note> notes = new HashSet<>();

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() { }

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

    public Contact(@Min(value = 3) @NotNull String firstname, @Min(value = 3) @NotNull String lastname, String website, @Email String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.website = website;
        this.email = email;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSellingProject() {
        return sellingProject;
    }

    public void setSellingProject(Boolean sellingProject) {
        this.sellingProject = sellingProject;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Boolean getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(Boolean ambassador) {
        this.ambassador = ambassador;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public LocalDateTime getOwnerSince() {
        return ownerSince;
    }

    public void setOwnerSince(DateAndTime ownerSince) {
        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        CharSequence inDateStr = null;
        this.ownerSince = LocalDateTime.parse(inDateStr, inFormat);
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setOwnerSince(LocalDateTime ownerSince) {
        this.ownerSince = ownerSince;
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
                ", phone='" + phone + '\'' +
                ", contact_status=" + contact_status +
                ", sellingProject=" + sellingProject +
                ", owner=" + owner +
                ", ambassador=" + ambassador +
                ", created_at=" + created_at +
                ", children=" + children +
                ", ownerSince=" + ownerSince +
                ", notes=" + notes +
                ", user=" + user +
                '}';
    }
}
