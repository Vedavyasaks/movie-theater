package com.jpmc.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jpmc.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookingDriver {

    List<Movie> movies;

    MovieController movieController;

    LocalDateProvider provider;

    private List<Show> schedule;

    List<Customer> customerList;

    private List<Schedule> printSchedule;

    public static void main(String[] args) {
        BookingDriver booking = new BookingDriver();
        Customer customer = new Customer(1,"Kevin");
        booking.createReservation(customer,5,3);
        booking.printSchedule();
        booking.handleJsonFormat();
    }
    public BookingDriver() {
        this.movieController = new MovieController();
        this.movies = new ArrayList<>();
        this.provider = LocalDateProvider.singleton();
        this.printSchedule = new ArrayList<>();
        initialise();
    }

    public void initialise(){
        createMovies();
        createShows();
        createCustomer();
    }

    private List<Customer> createCustomer() {
        customerList = List.of(new Customer(1,"Kevin"),new Customer(2,"Andrew"));
        return customerList;
    }

    private List<Show> createShows() {
        schedule = new ArrayList<>();
        Movie spiderMan = movieController.getMovieByMovieName("Spider-Man: No Way Home");
        Movie turningRed = movieController.getMovieByMovieName("Turning Red");
        Movie theBatMan = movieController.getMovieByMovieName("The Batman");
        schedule = List.of(
                new Show(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
                new Show(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
                new Show(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
                new Show(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
                new Show(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
                new Show(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
                new Show(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
                new Show(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
                new Show(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
        return schedule;
    }

    private void createMovies() {

        Movie spiderMan = new Movie("Spider-Man: No Way Home", "",Duration.ofMinutes(90), 12.5,1);
        Movie turningRed = new Movie("Turning Red","", Duration.ofMinutes(85), 11,0);
        Movie theBatMan = new Movie("The Batman","", Duration.ofMinutes(95), 9,0);

        movieController.addMovie(spiderMan);
        movieController.addMovie(turningRed);
        movieController.addMovie(theBatMan);

    }

    public Double createReservation(Customer customer, int sequence, int howManyTickets) {
        findCustomerByIdAndName(customer);
        Show showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            throw new ValidationException("Not able to find any showing for given sequence " + sequence);
        }
        Reservation reservation = new Reservation(customer, showing, howManyTickets);
        Bill bill = new Bill(reservation,howManyTickets);
        Double totalFee = bill.computeBillPayment();
        Payment payment = new Payment(provider);
        payment.payBill(totalFee);
        return totalFee;
    }

    public Customer findCustomerByIdAndName(Customer customerIn){
        for(Customer customer:customerList){
            if(customer.getCustomerId() == customerIn.getCustomerId() && customer.getCustomerName().equals(customerIn.getCustomerName())){
                return customer;
            }
        }
        throw new ValidationException("Customer Not Found! Please consider sending correct id and customer name ");
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->{
            System.out.println(s.getSequenceOfTheDay() + ": " + s.getShowStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovie().getTicketPrice());
            this.printSchedule.add(new Schedule(s.getSequenceOfTheDay(),startTimeFormat(s.getShowStartTime()),s.getMovie().getTitle(),s.getMovie().getTicketPrice(),humanReadableFormat(s.getMovie().getRunningTime())));
                }
        );
        System.out.println("===================================================");
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public void handleJsonFormat() {

        schedule.forEach(s ->{
            this.printSchedule.add(new Schedule(s.getSequenceOfTheDay(),startTimeFormat(s.getShowStartTime()),s.getMovie().getTitle(),s.getMovie().getTicketPrice(),humanReadableFormat(s.getMovie().getRunningTime())));
                }
        );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // 2. Java object to JSON string
        String jsonInString = gson.toJson(printSchedule);
        System.out.println("===================================================");
        System.out.println(jsonInString);
        System.out.println("===================================================");
    }

    public String startTimeFormat(LocalDateTime startTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Format LocalDateTime to String
        return startTime.format(dateTimeFormatter);
    }

}
