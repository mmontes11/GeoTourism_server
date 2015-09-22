package com.mmontes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    private Long id;
    private String username;
    private String password;
    private static final String ADMIN_ID_GENERATOR = "AdminIdGenerator";

    public Admin() {
    }

    public Admin(String password, String username) {
        this.password = password;
        this.username = username;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = ADMIN_ID_GENERATOR, sequenceName = "admin_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = ADMIN_ID_GENERATOR)
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
