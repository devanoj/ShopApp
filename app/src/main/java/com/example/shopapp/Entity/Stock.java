package com.example.shopapp.Entity;

import androidx.annotation.Nullable;

public class Stock {
    private String stockId;
    private String title;
    private String manufacturer;
    private double price;
    private String category;
    private String imageUrl;

    public Stock() {
        this.stockId="";
        this.title="";
        this.manufacturer="";
        this.price=0;
        this.category="";
        this.imageUrl="";
    }

    public Stock(String stockId, String title, String manufacturer, double price, String category, String imageUrl) {
        this.stockId=stockId;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
