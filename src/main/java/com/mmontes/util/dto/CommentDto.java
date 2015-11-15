package com.mmontes.util.dto;

public class CommentDto {

    private String commentText;
    private UserAccountDto user;

    public CommentDto() {
    }

    public CommentDto(String commentText, UserAccountDto user) {
        this.commentText = commentText;
        this.user = user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public UserAccountDto getUser() {
        return user;
    }

    public void setUser(UserAccountDto user) {
        this.user = user;
    }
}
