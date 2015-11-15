package com.mmontes.model.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "useraccount")
public class UserAccount {

    private static final String USERACCOUNT_ID_GENERATOR = "UserAccountIdGenerator";
    private Long id;
    private String name;
    private Calendar registrationDate;
    private Long facebookUserId;
    private String facebookProfilePhotoUrl;

    public UserAccount() {
    }

    public UserAccount(String name, Calendar registrationDate, Long facebookUserId, String facebookProfilePhotoUrl) {
        this.name = name;
        this.registrationDate = registrationDate;
        this.facebookUserId = facebookUserId;
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = USERACCOUNT_ID_GENERATOR, sequenceName = "useraccount_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = USERACCOUNT_ID_GENERATOR)
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
