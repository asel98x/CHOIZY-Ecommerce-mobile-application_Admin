package com.example.adminpanel.setters_getters;

import com.google.firebase.database.Exclude;

public class recommendedAds {
    String ad_ID, url,compID,type,title,key;

    public recommendedAds(String title, String url) {
        this.ad_ID = ad_ID;
        this.url = url;
        this.compID = compID;
        this.type = type;
        this.title = title;
    }

    public recommendedAds(){}

    @Exclude
    public String getKeey() {return key;}
    @Exclude
    public void setKeey(String key) {
        this.key = key;
    }
    public String getAd_ID() {
        return ad_ID;
    }

    public void setAd_ID(String ad_ID) {
        this.ad_ID = ad_ID;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
