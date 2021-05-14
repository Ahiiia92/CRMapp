package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "Class representing a viewing in the application")
@Entity
@Table(name = "Viewing")
public class Viewing {
    @ApiModelProperty(notes = "Unique identifier of the Viewing",
        required = true,
        position = 0)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(notes = "Date of the viewing",
        example = "TODO/ define DateTime format",
        required = true,
        position = 1)
    private Date viewingDate;

    @ApiModelProperty(notes = "Set of comments which is related to a viewing",
        required = true,
        position = 2)
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "contact")
    private Set<Comment> comments = new HashSet<>();

    public Viewing(Date viewingDate, Set<Comment> viewings) {
        this.viewingDate = viewingDate;
        this.comments = viewings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(Date viewingDate) {
        this.viewingDate = viewingDate;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
