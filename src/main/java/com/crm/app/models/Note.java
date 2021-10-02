package com.crm.app.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "Class representing a note in the application.")
@Entity
@Table(name = "Notes")
public class Note {
    @ApiModelProperty(notes = "Unique identifier of the Note",
            example = "1", required = true, position = 0)
    @Id
    @GeneratedValue
    @Column(name = "note_id")
    private Integer id;

    @ApiModelProperty(notes = "Content of a note about a contact or a viewing.",
            example = "Need to contact him again", required = true, position = 0)
    @Column(length = 4000)
    @NotNull(message = "Can't be empty")
    private String content;

    @ApiModelProperty(notes = "Creation date of a note", required = true, position = 1)
    @NotNull(message = "Can't be empty")
    private LocalDateTime creationDate;

    @ApiModelProperty(notes = "Due date for a viewing", required = false, position = 2)
    private LocalDateTime dueDate;

    @JsonBackReference
    @ManyToOne
    private Contact contact;

    @JsonBackReference
    @ManyToOne
    private Viewing viewing;

    public Note() {}

    public Note(String content, Contact contact) {
        this.content = content;
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Viewing getViewing() {
        return viewing;
    }

    public void setViewing(Viewing viewing) {
        this.viewing = viewing;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", viewing=" + viewing +
                '}';
    }
}

