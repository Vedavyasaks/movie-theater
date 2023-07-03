import com.jpmc.base.BookingDriver;
import com.jpmc.base.Customer;
import com.jpmc.exception.ValidationException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookingDriverTest {

    BookingDriver driver;

    @BeforeEach
    public void setup(){
        driver = new BookingDriver();
    }

    @Test
    @DisplayName("Book Customer With Unknown Customer")
    public void bookReservationWithUnknownCustomer(){
        Assertions.assertThrows(ValidationException.class,
                () -> {
            driver.createReservation(
                    new Customer(1,"Mac"),3,3);
        });
    }

    @Test
    @DisplayName("Print Schedule in Human Readable Format")
    public void printScheduleHumanReadableFormat(){
        driver.printSchedule();
    }

    @Test
    @DisplayName("Print Schedule in JSON Format")
    public void printScheduleInJsonFormat(){
        driver.handleJsonFormat();
    }

    @Test
    @DisplayName("Test Billing data with no discount")
    public void computeBill(){
        double bill = driver.createReservation(
                new Customer(1,"Kevin"),3,3);
        assertEquals(20.25,bill);
    }

    @AfterEach
    public void cleanUp(){
        driver = null;
    }


    }
