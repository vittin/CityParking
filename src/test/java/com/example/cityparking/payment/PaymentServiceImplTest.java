package com.example.cityparking.payment;

import com.example.cityparking.dao.model.CustomerModel;
import com.example.cityparking.dao.model.CustomerType;
import com.example.cityparking.dao.model.ParkModel;
import com.example.cityparking.payment.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    private CustomerModel exampleRegularCustomer;
    private CustomerModel exampleVIPCustomer;

    @Before
    public void setUp() throws Exception {
        exampleRegularCustomer = new CustomerModel("foo regular", null);
        exampleVIPCustomer = new CustomerModel("bar vip", null, CustomerType.VIP);
    }

    @Test
    public void given_regularUserWithParkTimeZero_when_checkFee_shouldReturnFeeForOneHour() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now(), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(1.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTimeZero_when_checkFee_shouldReturnFeeForOneHour() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now(), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(0.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime30min_when_checkFee_shouldReturnFeeForOneHour() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now().minus(30, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(1.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime30min_when_checkFee_shouldReturnFeeForOneHour() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now().minus(30, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(0.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime60min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now().minus(60, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(3.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime60min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now().minus(60, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(2.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime100min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now().minus(100, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(3.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime100min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now().minus(100, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(2.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime120min_when_checkFee_shouldReturnFeeForThreeHours() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now().minus(120, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((1.0) + (2.0) + (2.0*2.0), 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime120min_when_checkFee_shouldReturnFeeForThreeHours() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now().minus(120, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((0.0) + (2.0) + (2.0*1.5), 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime320min_when_checkFee_shouldReturnFeeForSixHours() {
        ParkModel parkModel = new ParkModel(exampleRegularCustomer, LocalDateTime.now().minus(320, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((1.0) + (2.0) + (2.0 * 2) + (2.0 * 2*2) + + (2.0 * 2*2*2) + (2.0 * 2*2*2*2),  0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime320min_when_checkFee_shouldReturnFeeForSixHours() {
        ParkModel parkModel = new ParkModel(exampleVIPCustomer, LocalDateTime.now().minus(320, ChronoUnit.MINUTES), null);

        double price = paymentService.calculatePrice(parkModel, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(
                (0.0) + (2.0) + (2.0 * 1.5) + (2.0 * 1.5*1.5) + (2.0 * 1.5*1.5*1.5) + (2.0 * 1.5*1.5*1.5*1.5),
                0.0001)));
    }
}