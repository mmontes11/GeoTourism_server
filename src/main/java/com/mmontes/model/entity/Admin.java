package com.mmontes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    private Long id;
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String password, String username) {
        this.password = password;
        this.username = username;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = "AdminIdGenerator", sequenceName = "admin_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AdminIdGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
