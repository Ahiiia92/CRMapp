package com.crm.app.payload;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginDto {
    @Id
    private Long id;
    private String usernameOrEmail;
    private String password;


    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
