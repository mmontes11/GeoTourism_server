package com.mmontes.model.entity;

import com.mmontes.model.entity.TIP.TIP;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "rating")
public class Rating {

    private Long id;
    private Double ratingValue;
    private Calendar ratingDate;
    private UserAccount userAccount;
    private TIP tip;
    private static final String RATING_ID_GENERATOR = "RatingIdGenerator";

    public Rating() {
    }

    public Rating(Long id, Double ratingValue, Calendar ratingDate, UserAccount userAccount, TIP tip) {
        this.id = id;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.userAccount = userAccount;
        this.tip = tip;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = RATING_ID_GENERATOR, sequenceName = "rating_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = RATING_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    @Column(name = "ratingvalue")
    public void setId(Long id) {
        this.id = id;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    @Column(name = "ratingdate")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Calendar ratingDate) {
        this.ratingDate = ratingDate;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipid")
    public TIP getTip() {
        return tip;
    }

    public void setTip(TIP tip) {
        this.tip = tip;
    }
}
