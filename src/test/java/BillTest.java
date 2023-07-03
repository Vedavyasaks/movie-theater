
import com.jpmc.base.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillTest {

    Reservation reservation;

    Bill bill;

    Movie movie;

    Show show;

    LocalDateProvider provider;

    @BeforeEach
    public void setup(){
        provider = LocalDateProvider.singleton();
        Customer customer = new Customer(1,"John");
        movie = new Movie("Turning Red","", Duration.ofMinutes(85), 11,0);
        show = new Show(movie,5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        reservation = new Reservation(customer,show,4);
    }

    @Test
    @DisplayName("Test Billing data with no discount")
    public void computeBillValueTest(){
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(44,computedValue);
    }

    @Test
    @DisplayName("Test Billing data with special code")
    public void computeBillValueWithSpecialCodeTest(){
        movie.setSpecialCode(1);
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(35.2,computedValue);
    }

    @Test
    @DisplayName("Test Billing data with sequence No 1")
    public void computeBillValueWithSequenceNo1(){
        show.setSequenceOfTheDay(1);
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(41,computedValue);
    }

    @Test
    @DisplayName("Test Billing data with sequence No 2")
    public void computeBillValueWithSequenceNo2(){
        show.setSequenceOfTheDay(2);
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(42,computedValue);
    }

    @Test
    @DisplayName("Test Billing data with sequence No 7")
    public void computeBillValueWithSequenceNo7(){
        show.setSequenceOfTheDay(7);
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(43,computedValue);
    }

    @Test
    @DisplayName("Test Billing data within Time Range")
    public void computeBillValueWithinTimeRangeTest(){
        show.setShowStartTime(LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 01)));
        bill = new Bill(reservation, movie.getTicketPrice()*reservation.getNoOfSeats());
        double computedValue =  bill.computeBillPayment();
        assertEquals(33,computedValue);
    }


    @AfterEach
    public void cleanUp(){
        reservation = null;
         bill= null;
         movie= null;
         show= null;
         provider= null;
    }
}
