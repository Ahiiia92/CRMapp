package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.*;
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
    @Column(name = "contact_id")
    private Integer id;

    @ApiModelProperty(notes = "Firstname and lastname of the contact.",
            example = "Jessica Abigail", required = true, position = 1)
    @Min(value = 3, message = "Too short entry")
    @NotNull
    private String firstname, lastname;

    @ApiModelProperty(notes = "Job and phone number of the contact.",
            example = "Doctor", required = false, position = 2)
    private String profession, phone;

    @ApiModelProperty(notes = "Full address of the contact",
            example = "221B Backer Street NW1 6XE London",
            required = true,
            position = 3)
    @NotNull(message = "Can't be empty")
    private String address;

    @ApiModelProperty(notes = "Timestamp of the contact creation date", required = true)
    @CreatedDate
    private Instant created_at;

    // within the contact view, the last viewing Date should be displayed
    private LocalDateTime nextViewingDate;

    @ApiModelProperty(notes = "Check if there is a doorbell or not", required = true)
    // A contact is either a buyer or a seller
    private Boolean sellingProject, owner, ambassador, doorBell;

    @Email
    private String email;

    @Enumerated(value = EnumType.STRING)
    private SocialMedia socialMedia;

    @Enumerated(value = EnumType.STRING)
    private Contact_status contact_status;

    // How many children have our contact
    @ApiModelProperty(notes = "Check if there is a doorbell or not", required = false)
    private Integer children;

    @DateTimeFormat(pattern = "dd LLL. yyyy")
    private LocalDate ownerSince;

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
        this.created_at = Instant.now();
    }

    public Contact(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Contact(@Min(value = 3) @NotNull String firstname, @Min(value = 3) @NotNull String lastname, @Email String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = Instant.now();
    }

    public LocalDateTime getNextViewingDate() {
        return nextViewingDate;
    }

    public void setNextViewingDate(LocalDateTime nextViewingDate) {
        this.nextViewingDate = nextViewingDate;
    }

    public Boolean getDoorBell() {
        return doorBell;
    }

    public void setDoorBell(Boolean doorBell) {
        this.doorBell = doorBell;
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

    public LocalDate getOwnerSince() {
        return ownerSince;
    }

    public void setOwnerSince(LocalDate ownerSince) {
        this.ownerSince = ownerSince;
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
