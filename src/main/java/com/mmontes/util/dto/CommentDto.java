package com.mmontes.util.dto;

public class CommentDto {

    private String commentText;
    private UserDto user;

    public CommentDto() {
    }

    public CommentDto(String commentText, UserDto user) {
        this.commentText = commentText;
        this.user = user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
