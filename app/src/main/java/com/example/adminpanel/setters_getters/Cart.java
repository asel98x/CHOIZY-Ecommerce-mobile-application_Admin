package com.example.adminpanel.setters_getters;

public class Cart {

    Offer offer;
    int qut;

    public Cart(Offer offer, int qut) {
        this.offer = offer;
        this.qut = qut;
    }

    public Cart(){

    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public int getQut() {
        return qut;
    }

    public void setQut(int qut) {
        this.qut = qut;
    }
}
