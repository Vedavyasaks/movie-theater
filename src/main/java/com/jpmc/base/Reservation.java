package com.jpmc.base;

public class Reservation {

    Customer customer;
    Show show;
    int noOfSeats;

    public Reservation(Customer customer, Show show, int noOfSeats) {
        this.customer = customer;
        this.show = show;
        this.noOfSeats = noOfSeats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}
