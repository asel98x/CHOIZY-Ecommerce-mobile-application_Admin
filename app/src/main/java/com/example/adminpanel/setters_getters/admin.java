package com.example.adminpanel.setters_getters;

import com.google.firebase.database.Exclude;

public class admin {

    public String admin_name;
    public String admin_email;
    public String admin_mobile;
    public String admin_gender;
    public String admin_nic;
    public String admin_address;
    public String admin_password;
    public String imageURL;
    private String key;

    public admin(){}

    @Exclude
    public String getKeey() {return key;}
    @Exclude
    public void setKeey(String key) {
        this.key = key;
    }

    public admin(String admin_name, String admin_email, String admin_gender, String admin_nic, String admin_mobile, String admin_address, String admin_password, String imageURL) {
        this.admin_name = admin_name;
        this.admin_email = admin_email;
        this.admin_gender = admin_gender;
        this.admin_nic = admin_nic;
        this.admin_mobile = admin_mobile;
        this.admin_address = admin_address;
        this.admin_password = admin_password;
        this.imageURL = imageURL;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_mobile() {
        return admin_mobile;
    }

    public void setAdmin_mobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public String getAdmin_gender() {
        return admin_gender;
    }

    public void setAdmin_gender(String admin_gender) {
        this.admin_gender = admin_gender;
    }

    public String getAdmin_nic() {
        return admin_nic;
    }

    public void setAdmin_nic(String admin_nic) {
        this.admin_nic = admin_nic;
    }

    public String getAdmin_address() {
        return admin_address;
    }

    public void setAdmin_address(String admin_address) {
        this.admin_address = admin_address;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
