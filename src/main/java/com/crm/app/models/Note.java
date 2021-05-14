package com.crm.app.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(description = "Class representing a note in the application.")
@Entity
@Table
public class Note {
    @ApiModelProperty(notes = "Unique identifier of the Note",
            example = "1", required = true, position = 0)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "Notes about the contact.",
            example = "Need to contact him again", required = false, position = 0)
    @Column(length = 4000)
    private String content;

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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", contact=" + contact +
                '}';
    }
}

