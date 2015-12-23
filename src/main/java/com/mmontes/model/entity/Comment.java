package com.mmontes.model.entity;

import com.mmontes.model.entity.TIP.TIP;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "comment")
public class Comment {

    private Long id;
    private String commentText;
    private Calendar commentDate;
    private UserAccount userAccount;
    private TIP tip;
    private static final String  COMMENT_ID_GENERATOR = "CommentIdGenerator";

    public Comment() {
    }

    public Comment(String commentText, Calendar commentDate) {
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = COMMENT_ID_GENERATOR, sequenceName = "comment_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = COMMENT_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "commenttext")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Column(name = "commentdate")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Calendar commentDate) {
        this.commentDate = commentDate;
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
