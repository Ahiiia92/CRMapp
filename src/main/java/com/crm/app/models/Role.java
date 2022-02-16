package com.crm.app.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Integer id;
    private String roleName;

    public Role() {};

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + roleName;
    }
}
