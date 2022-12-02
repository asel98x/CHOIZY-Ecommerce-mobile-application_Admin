package com.example.adminpanel.setters_getters;

import com.google.firebase.database.Exclude;

public class category {
    public String category_name;
    public String category_colour;
    public String imageURL;
    private String key;

    public category(){}

    public category(String category_name, String category_colour, String imageURL) {
        this.category_name = category_name;
        this.category_colour = category_colour;
        this.imageURL = imageURL;
    }

    @Exclude
    public String getKeey() {
        return key;
    }
    @Exclude
    public void setKeey(String key) {
        this.key = key;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_colour() {
        return category_colour;
    }

    public void setCategory_colour(String category_colour) {
        this.category_colour = category_colour;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
