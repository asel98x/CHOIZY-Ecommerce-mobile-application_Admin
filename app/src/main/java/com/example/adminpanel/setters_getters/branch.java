package com.example.adminpanel.setters_getters;

import com.google.firebase.database.Exclude;

public class branch {
    String ID, Name, Mobile, Email, No_adres, streetAddress, City, Password, role, compID;
    double latitude;
    double longitude;
    boolean haveDelivering = false;
    boolean haveAdvance = false;
    private String key;

    public branch (){}

    public branch(String ID, String name, String mobile, String email, String no_adres, String streetAddress, String city, String password, String role, String compID, int latitude, int longitude) {
        this.ID = ID;
        Name = name;
        Mobile = mobile;
        Email = email;
        No_adres = no_adres;
        this.streetAddress = streetAddress;
        City = city;
        Password = password;
        this.role = role;
        this.compID = compID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.key = key;
    }
    @Exclude
    public String getKeey() {
        return key;
    }
    @Exclude
    public void setKeey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNo_adres() {
        return No_adres;
    }

    public void setNo_adres(String no_adres) {
        No_adres = no_adres;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isHaveDelivering() {
        return haveDelivering;
    }

    public void setHaveDelivering(boolean haveDelivering) {
        this.haveDelivering = haveDelivering;
    }

    public boolean isHaveAdvance() {
        return haveAdvance;
    }

    public void setHaveAdvance(boolean haveAdvance) {
        this.haveAdvance = haveAdvance;
    }
}
