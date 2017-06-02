package com.um1616.carticketinfo.model;

/**
 * Created by UCSM on 10/22/2016.
 */

public class Gate {
    private int id;
    private String name, route, contact, price;

    public Gate(int id, String name, String route, String contact, String price) {
        this.id = id;
        this.name = name;
        this.route = route;
        this.contact = contact;
        this.price = price;
    }

    public Gate(String name, String route, String contact, String price) {
        this.name = name;
        this.route = route;
        this.contact = contact;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
