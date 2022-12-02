package com.example.adminpanel.setters_getters;

import com.google.firebase.database.Exclude;

public class upcommingAds {
    String Id, url,compID,type,key;

    public upcommingAds(){}

    public upcommingAds(String ad_ID, String url, String compID, String type) {
        this.Id = ad_ID;
        this.url = url;
        this.compID = compID;
        this.type = type;
        this.key = key;
    }
    @Exclude
    public String getKeey() {return key;}
    @Exclude
    public void setKeey(String key) {
        this.key = key;
    }
    public String getAd_ID() {
        return Id;
    }

    public void setAd_ID(String ad_ID) {
        this.Id = ad_ID;
    }

    public String getUrl() {
        return url;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
