package com.mmontes.util.dto;

public class CommentDto {

    private Long id;
    private String commentText;
    private Long timeInMillis;
    private UserAccountDto user;

    public CommentDto() {
    }

    public CommentDto(Long id, String commentText, Long timeInMillis, UserAccountDto user) {
        this.id = id;
        this.commentText = commentText;
        this.timeInMillis = timeInMillis;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(Long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public UserAccountDto getUser() {
        return user;
    }

    public void setUser(UserAccountDto user) {
        this.user = user;
    }
}
