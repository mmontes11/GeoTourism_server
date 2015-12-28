package com.mmontes.model.entity;

import com.mmontes.model.entity.route.Route;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "useraccount")
public class UserAccount {

    private static final String USERACCOUNT_ID_GENERATOR = "UserAccountIdGenerator";
    private Long id;
    private String name;
    private Calendar registrationDate;
    private Long facebookUserId;
    private String facebookProfileUrl;
    private String facebookProfilePhotoUrl;
    private Set<UserAccount> friends = new HashSet<>();
    private Set<Route> createdRoutes = new HashSet<>();

    public UserAccount() {
    }

    public UserAccount(Long id, String name, Calendar registrationDate, Long facebookUserId, String facebookProfileUrl, String facebookProfilePhotoUrl, Set<UserAccount> friends, Set<Route> createdRoutes) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.facebookUserId = facebookUserId;
        this.facebookProfileUrl = facebookProfileUrl;
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
        this.friends = friends;
        this.createdRoutes = createdRoutes;
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

    @Column(name = "facebookprofileurl")
    public String getFacebookProfileUrl() {
        return facebookProfileUrl;
    }

    public void setFacebookProfileUrl(String facebookProfileUrl) {
        this.facebookProfileUrl = facebookProfileUrl;
    }

    @Column(name = "facebookprofilephotourl")
    public String getFacebookProfilePhotoUrl() {
        return facebookProfilePhotoUrl;
    }

    public void setFacebookProfilePhotoUrl(String facebookProfilePhotoUrl) {
        this.facebookProfilePhotoUrl = facebookProfilePhotoUrl;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "useraccountuseraccount",
            joinColumns = {
                    @JoinColumn(name = "userAccountId", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "friendId", nullable = false, updatable = false)
            }
    )
    public Set<UserAccount> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserAccount> friends) {
        this.friends = friends;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    public Set<Route> getCreatedRoutes() {
        return createdRoutes;
    }

    public void setCreatedRoutes(Set<Route> createdRoutes) {
        this.createdRoutes = createdRoutes;
    }
}
