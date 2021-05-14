package com.crm.app.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "Class representing a note in the application.")
@Entity
@Table
public class Note {
    @ApiModelProperty(notes = "Unique identifier of the Note",
            example = "1", required = true, position = 0)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "Content of a note about a contact or a viewing.",
            example = "Need to contact him again", required = true, position = 0)
    @Column(length = 4000)
    @NotNull
    private String content;

    @ApiModelProperty(notes = "Creation date of a note", required = true, position = 1)
    @NotNull
    private Date creationDate;

    @ApiModelProperty(notes = "Due date for a viewing", required = false, position = 2)
    private Date dueDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
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

