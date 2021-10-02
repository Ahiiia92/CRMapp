package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "Class representing a viewing in the application")
@Entity
@Table(name = "Viewings")
public class Viewing {
    @ApiModelProperty(notes = "Unique identifier of the Viewing",
        required = true,
        position = 0,
        example = "1")
    @Id
    @GeneratedValue
    @Column(name = "viewing_id")
    private Integer id;

    @ApiModelProperty(notes = "Date of the viewing",
        example = "TODO: define DateTime format",
        required = true,
        position = 1)
    @NotNull(message = "Can't be empty")
    private LocalDateTime viewingDate;

    @ApiModelProperty(notes = "Set of notes which is related to a viewing",
        required = true,
        position = 2)
    @JsonManagedReference
    @NotNull(message = "Can't be empty")
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "contact")
    private Set<Note> notes = new HashSet<>();

    public Viewing() {}

    public Viewing(LocalDateTime viewingDate, Set<Note> notes) {
        this.viewingDate = viewingDate;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(LocalDateTime viewingDate) {
        this.viewingDate = viewingDate;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setComments(Set<Note> notes) {
        this.notes = notes;
    }
}
