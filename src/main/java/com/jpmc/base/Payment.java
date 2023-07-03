package com.jpmc.base;

public class Payment {

    LocalDateProvider provider;
    public void payBill( double totalFee){
        System.out.println("Paid :"+ totalFee);
    }
    public Payment(LocalDateProvider provider) {
        this.provider = provider;
    }

}
