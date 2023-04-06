package com.example.shopapp.Entity;

public class Comment {
    private String cID;
    private String comment;
    private String stockCommentId;
    private String userCommentID;

    public Comment() {
        this.cID="";
        this.comment = "";
        this.stockCommentId = "";
        this.userCommentID=userCommentID;
    }

    public Comment(String cID, String comment, String stockCommentId, String userCommentID) {
        this.cID=cID;
        this.comment = comment;
        this.stockCommentId = stockCommentId;
        this.userCommentID = userCommentID;
    }

    public String getUserCommentID() {
        return userCommentID;
    }

    public void setUserCommentID(String userCommentID) {
        this.userCommentID = userCommentID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStockCommentId() {
        return stockCommentId;
    }

    public void setStockCommentId(String userCommentId) {
        this.stockCommentId = userCommentId;
    }
}
