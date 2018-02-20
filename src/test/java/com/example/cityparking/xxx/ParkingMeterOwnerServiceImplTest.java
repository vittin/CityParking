package com.example.cityparking.xxx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        //examplePark = parkRepository.saveAndFlush(examplePark);
    }

    @Test
    public void given_emptyDB_when_getCashSummary_then_shouldReturnEmptyMap() {
        //test
        Map<Currency, Double> cashSummary = parkingMeterOwnerService.cashSummary(LocalDate.now());

        //validate
        assertThat(cashSummary, is(not(nullValue())));
        assertThat(cashSummary.keySet(), is(empty()));
    }

    @Test
    public void given_event_when_getCashSummary_shouldReturnCash() {
        //set
        Currency currency = Currency.getInstance("PLN");
        examplePark.setEndDate(LocalDateTime.now());
        examplePark.setPrice(new Price(currency, 5));
        examplePark = parkRepository.saveAndFlush(examplePark);

        //test
        Double cash = parkingMeterOwnerService.cashSummary(LocalDate.now(), currency);

        //validate
        assertThat(cash, is(5.0));
    }

    @Test
    public void given_multipleEvents_when_getCashSummaryWithCurrency_shouldReturnCash() {
        //set
        Currency plnCurrency = Currency.getInstance("PLN");
        Currency usdCurrency = Currency.getInstance("USD");

        examplePark.setEndDate(LocalDateTime.now());
        examplePark.setPrice(new Price(plnCurrency, 5));

        ParkModel exampleUSDPark = new ParkModel(
                exampleCustomer,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(4,0)),
                new Price(usdCurrency, 3));

        examplePark = parkRepository.save(examplePark);
        exampleUSDPark = parkRepository.saveAndFlush(exampleUSDPark);
        //test
        Double cashPLN = parkingMeterOwnerService.cashSummary(LocalDate.now(), plnCurrency);
        Double cashUSD = parkingMeterOwnerService.cashSummary(LocalDate.now(), usdCurrency);

        //validate
        assertThat(cashPLN, is(not(nullValue())));
        assertThat(cashPLN, is(5.0));
        assertThat(cashUSD, is(not(nullValue())));
        assertThat(cashUSD, is(3.0));
    }

    @Test
    public void given_multipleEvents_when_getCashSummary_shouldReturnSummaryCash() {
        //set
        Currency plnCurrency = Currency.getInstance("PLN");
        Currency usdCurrency = Currency.getInstance("USD");

        examplePark.setEndDate(LocalDateTime.now());
        examplePark.setPrice(new Price(plnCurrency, 5));

        ParkModel exampleUSDPark = new ParkModel(
                exampleCustomer,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(4,0)),
                new Price(usdCurrency, 3));

        parkRepository.save(examplePark);
        parkRepository.saveAndFlush(exampleUSDPark);
        //test
        Map<Currency, Double> cash = parkingMeterOwnerService.cashSummary(LocalDate.now());

        //validate
        assertThat(cash, is(not(nullValue())));
        assertThat(cash, hasKey(usdCurrency));
        assertThat(cash, hasKey(plnCurrency));
    }

    @Test
    public void given_customerName_when_getCustomerInfo_then_shouldReturnCustomerPlaces() {
        //set

        //test
        CustomerModel customer = parkingMeterOwnerService.customerInfo("foo bar");

        //validate
        assertThat(customer, is(not(nullValue())));
        assertThat(customer.getParkPlaces(), is(not(empty())));
        assertThat(customer.getParkPlaces(), hasItem(examplePark));
    }

    @Test
    public void given_wrongCustomerName_when_getCustomerInfo_then_shouldReturnNull() {
        //set

        //test
        CustomerModel customer = parkingMeterOwnerService.customerInfo("not existing foo bar");

        //validate
        assertThat(customer, is(nullValue()));
    }

    @Test
    public void given_parkID_when_getParkInfo_shouldReturnCustomer() {
        //set

        //test
        ParkModel parkModel = parkingMeterOwnerService.parkInfo(examplePark.getId());

        //validate
        assertThat(parkModel, is(not(nullValue())));
        assertThat(parkModel.getCustomer(), is(not(nullValue())));
        assertThat(parkModel.getCustomer(), is(exampleCustomer));
    }

    @Test
    public void given_wrongParkID_when_getParkInfo_shouldReturnNull() {
        //set

        //test
        ParkModel parkModel = parkingMeterOwnerService.parkInfo(examplePark.getId()+1L);

        //validate
        assertThat(parkModel, is(nullValue()));
    }

    @Test
    public void given_currency_when_getCashSummaryWithNoEvents_shouldReturnZeroCash() {
        //set
        Currency currency = Currency.getInstance("PLN");

        //test
        Double cash = parkingMeterOwnerService.cashSummary(LocalDate.now().plus(2, ChronoUnit.DAYS), currency);

        //validate
        assertThat(cash, is(0.0));
    }
}