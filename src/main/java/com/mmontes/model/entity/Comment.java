package com.mmontes.model.entity;

import javax.persistence.*;
import java.util.Calendar;

public class Comment {

    private Long id;
    private String commentText;
    private Calendar commentDate;
    private UserAccount user;
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
    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
