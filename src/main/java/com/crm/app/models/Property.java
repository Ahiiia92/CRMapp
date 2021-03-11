package com.crm.app.models;

import javax.persistence.*;

@Entity
public class Property {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Integer room;
    private Double prize, size;

    @ManyToOne(fetch= FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Property() {}

    public Property(String title, Integer room, Double prize, Double size) {
        this.title = title;
        this.room = room;
        this.prize = prize;
        this.size = size;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
