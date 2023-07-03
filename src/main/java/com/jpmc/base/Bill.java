package com.jpmc.base;
import java.time.LocalTime;
import java.util.Objects;

public class Bill {

    private static int MOVIE_CODE_SPECIAL = 1;

    private Reservation reservation;

    private double totalAmount;

    public double computeBillPayment(){
        double totalAmountBeforeDiscount = reservation.getNoOfSeats() * reservation.getShow().getMovie().getTicketPrice();
        System.out.println("totalAmountBeforeDiscount:"+totalAmountBeforeDiscount);
        double totalAmountAfterDiscount = totalAmountBeforeDiscount - getDiscount(totalAmountBeforeDiscount,reservation.getShow().getMovie().getSpecialCode());
        System.out.println("totalAmountAfterDiscount:"+totalAmountAfterDiscount);
        return totalAmountAfterDiscount;
    }

    private double getDiscount(double totalAmountBeforeDiscount, int couponCode) {
        LocalTime time = reservation.getShow().getShowStartTime().toLocalTime();
        boolean withinTimeRange = isBetween(time, LocalTime.parse("11:00"), LocalTime.parse("16:00"));

        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == couponCode && totalAmountBeforeDiscount * 0.2 > specialDiscount) {
            specialDiscount = totalAmountBeforeDiscount * 0.2;  // 20% discount for special movie
        }
        if(reservation.getShow().getSequenceOfTheDay() == 1 && 3 > specialDiscount){
            specialDiscount = 3;
        }
        if(reservation.getShow().getSequenceOfTheDay() == 2 && 2 > specialDiscount){
            specialDiscount = 2;
        }
        if(withinTimeRange && totalAmountBeforeDiscount * 0.25 > 2){
            specialDiscount = totalAmountBeforeDiscount * 0.25;
        }
        if(reservation.getShow().getSequenceOfTheDay() == 7 && 1 > specialDiscount){
            specialDiscount = 1;
        }
        return specialDiscount;
    }
    private static <C extends Comparable<? super C>> boolean isBetween(C value, C start, C end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) < 0;
    }
    public Bill(Reservation reservation, double totalAmount) {
        this.reservation = reservation;
        this.totalAmount = totalAmount;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill that = (Bill) o;
        return Double.compare(that.totalAmount, totalAmount) == 0 && Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, totalAmount);
    }
}
