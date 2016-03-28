package com.mmontes.model.entity;

import com.mmontes.model.entity.TIP.TIP;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "favourite")
public class Favourite {

    private Long id;
    private Calendar favouriteDate;
    private UserAccount userAccount;
    private TIP tip;
    private static final String FAVOURITE_ID_GENERATOR = "FavouriteIdGenerator";

    public Favourite() {
    }

    public Favourite(Long id, Calendar favouriteDate, UserAccount userAccount, TIP tip) {
        this.id = id;
        this.favouriteDate = favouriteDate;
        this.userAccount = userAccount;
        this.tip = tip;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = FAVOURITE_ID_GENERATOR, sequenceName = "comment_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = FAVOURITE_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "favouritedate")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getFavouriteDate() {
        return favouriteDate;
    }

    public void setFavouriteDate(Calendar favouriteDate) {
        this.favouriteDate = favouriteDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipid")
    public TIP getTip() {
        return tip;
    }

    public void setTip(TIP tip) {
        this.tip = tip;
    }
}
