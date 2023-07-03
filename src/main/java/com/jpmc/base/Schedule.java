package com.jpmc.base;

import java.time.LocalDateTime;

public class Schedule {

    private int sequenceOfTheDay;

    private String showStartTime;

    private String title;

    private double ticketPrice;

    private String  duration;

    public Schedule(int sequenceOfTheDay, String showStartTime, String title, double ticketPrice, String duration) {
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        this.title = title;
        this.ticketPrice = ticketPrice;
        this.duration = duration;
    }
}
