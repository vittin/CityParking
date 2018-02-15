package com.example.cityparking.xxx;

import com.example.cityparking.currency.PolishCurrency;
import com.example.cityparking.currency.USACurrency;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({PolishCurrency.class, USACurrency.class})
public class ParkingMeterOwnerServiceImplTest {

    @Autowired
    private ParkingMeterOwnerService parkingMeterOwnerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ParkRepository parkRepository;

    private CustomerModel exampleCustomer;
    private ParkModel examplePark;

    @Before
    public void setUp() throws Exception {
        exampleCustomer = new CustomerModel("foo bar", null);
        examplePark = new ParkModel(
                exampleCustomer,
                LocalDateTime.ofInstant(Instant.now().minus(3, ChronoUnit.HOURS), ZoneId.of("UTC")),
                LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
        );
        exampleCustomer.setParkPlaces(Collections.singletonList(examplePark));

        exampleCustomer = customerRepository.saveAndFlush(exampleCustomer);
    }

    @After
    public void tearDown() throws Exception {
        customerRepository.deleteAll();
        parkRepository.deleteAll();
    }

    @Test
    public void when_getCashSummary_then_shouldReturnAnyCurrency() {
        //set

        //test
        Map<Currency, Integer> cashSummary = parkingMeterOwnerService.cashSummary(LocalDate.now());

        //validate
        assertThat(cashSummary, is(not(nullValue())));
        assertThat(cashSummary, hasKey(Currency.getInstance("PLN")));
    }

    @Test
    public void given_customer_when_getCustomerInfo_then_shouldReturnCustomerPlaces() {
        //set

        //test
        CustomerModel customer = parkingMeterOwnerService.customerInfo("foo bar");

        //validate
        assertThat(customer, is(not(nullValue())));
        assertThat(customer.getParkPlaces(), is(not(empty())));
        assertThat(customer.getParkPlaces().get(0), is(examplePark));
    }


}