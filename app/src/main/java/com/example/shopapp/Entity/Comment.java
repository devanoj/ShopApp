package com.example.shopapp.Entity;

public class Comment {
    private String comment;
    private String userCommentId;

    public Comment() {
        this.comment = "";
        this.userCommentId = "";
    }

    public Comment(String comment, String userCommentId) {
        this.comment = comment;
        this.userCommentId = userCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserCommentId() {
        return userCommentId;
    }

    public void setUserCommentId(String userCommentId) {
        this.userCommentId = userCommentId;
    }
}
