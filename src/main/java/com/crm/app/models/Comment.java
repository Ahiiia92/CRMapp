package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 300)
    private String content;

    @JsonBackReference
    @ManyToOne
    private Contact contact;

    public Comment() {}

    public Comment(String content, Contact contact) {
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
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", contact=" + contact +
                '}';
    }
}
