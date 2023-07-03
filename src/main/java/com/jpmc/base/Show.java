package com.jpmc.base;

import java.time.LocalDateTime;
import java.util.Objects;

public class Show {

    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Show(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public void setSequenceOfTheDay(int sequenceOfTheDay) {
        this.sequenceOfTheDay = sequenceOfTheDay;
    }

    public LocalDateTime getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(LocalDateTime showStartTime) {
        this.showStartTime = showStartTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show Show = (Show) o;
        return sequenceOfTheDay == Show.sequenceOfTheDay && Objects.equals(movie, Show.movie) && Objects.equals(showStartTime, Show.showStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, sequenceOfTheDay, showStartTime);
    }
}
