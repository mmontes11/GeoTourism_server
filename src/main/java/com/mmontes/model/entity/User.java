package com.mmontes.model.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "user")
public class User {

    private static final String USER_ID_GENERATOR = "UserIdGenerator";
    private Long id;
    private String name;
    private String surname;
    private Calendar registrationDate;
    private Long facebookUserId;
    private String facebookProfilePhotoUrl;

    public User() {
    }

    public User(String name, String surname, Calendar registrationDate, Long facebookUserId, String facebookProfilePhotoUrl) {
        this.name = name;
        this.surname = surname;
        this.registrationDate = registrationDate;
        this.facebookUserId = facebookUserId;
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = USER_ID_GENERATOR, sequenceName = "user_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = USER_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "registrationdate")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Calendar registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Column(name = "facebookuserid")
    public Long getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(Long facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    @Column(name = "facebookprofilephotourl")
    public String getFacebookProfilePhotoUrl() {
        return facebookProfilePhotoUrl;
    }

    public void setFacebookProfilePhotoUrl(String facebookProfilePhotoUrl) {
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
    }
}
