package com.example.shopapp.Entity;

import androidx.annotation.Nullable;

public class Stock {
    private String stockId;
    private String title;
    private String manufacturer;
    private String price;
    private String category;
    private String imageUrl;
    private String quantity;
    private Comment comment;

    public Stock() {
        this.stockId="";
        this.title="";
        this.manufacturer="";
        this.price="";
        this.category="";
        this.imageUrl="";
        this.quantity="";
        this.comment=comment;
    }

    public Stock(String stockId, String title, String manufacturer, String price, String category, String imageUrl, String quantity, Comment comment) {
        this.stockId=stockId;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity=quantity;
        this.comment=comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

}
