package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@Entity
@Table(name = "CRMUsers")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User implements UserDetails {
    private static final long serialVersionUID = 2396654715019746670L;

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name = "user_id")
    private Integer id;
    private String firstname, lastname, email, password, username;

    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Contact> contacts;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonCreator
    User(@JsonProperty("id") final Integer id,
         @JsonProperty("username") final String username,
         @JsonProperty("password") final String password,
         @JsonProperty("firstname") final String firstname,
         @JsonProperty("lastname") final String lastname,
         @JsonProperty("email") final String email,
         @JsonProperty("contacts") final List<Contact> contacts,
         @JsonProperty("role") final Role role) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contacts = contacts;
        this.role = role;
    }

    /**
     *
    public User() { super(); }

    public User(Integer id, String firstname, String lastname, String email, String password, String username, List<Contact> contacts, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.contacts = contacts;
        this.role = role;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String password, String username, Role role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }
     */

    public Role getRole() {
        return role;
    }


    public Integer getId() {
        return id;
    }


    public String getFirstname() {
        return firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
