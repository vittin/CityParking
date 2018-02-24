package com.example.cityparking.customer;

import com.example.cityparking.customer.ParkingMeterCustomerService;
import com.example.cityparking.dao.*;
import com.example.cityparking.dao.model.CustomerModel;
import com.example.cityparking.dao.model.ParkModel;
import com.example.cityparking.dao.model.Price;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParkingMeterCustomerServiceImplTest {

    @Autowired
    ParkingMeterCustomerService customerService;
    @Autowired
    ParkRepository parkRepository;
    @Autowired
    CustomerRepository customerRepository;

    private CustomerModel exampleCustomer;
    private ParkModel examplePark;

    @Before
    public void setUp() throws Exception {
        exampleCustomer = new CustomerModel("foo bar", null);
        examplePark = new ParkModel(
                exampleCustomer,
                LocalDateTime.now(),
                null
        );
        exampleCustomer.setParkPlaces(Collections.singletonList(examplePark));

        exampleCustomer = customerRepository.saveAndFlush(exampleCustomer);
        //examplePark = parkRepository.saveAndFlush(examplePark);
    }

    @Test
    public void when_startParking_then_shouldCreateCustomerEvent() {
        customerService.start("John Snow");

        CustomerModel customerModel = customerRepository.findByIdentity("John Snow");

        assertThat(customerModel, is(not(nullValue())));
        assertThat(customerModel.getParkPlaces(), is(not(empty())));
    }

    @Test
    public void when_startParking_then_shouldCreateParkEvent() {
        customerService.start("John Snow");

        List<ParkModel> parkModelList = parkRepository.findAllByCustomerIdentity("John Snow");

        assertThat(parkModelList, is(not(empty())));
        assertThat(parkModelList.get(0).getCustomer(), hasProperty("identity", equalTo("John Snow")));
    }

    @Test
    public void when_stopParking_then_shouldGenerateEndDate() {
        makePayment(examplePark);
        customerService.stop(exampleCustomer.getIdentity(), examplePark.getId());

        exampleCustomer = customerRepository.findByIdentity(exampleCustomer.getIdentity());

        assertThat(exampleCustomer.getParkPlaces(), hasItem(hasProperty("endDate", is(not(nullValue())))));
    }

    @Test
    public void given_RegularParkUnderHour_when_chceckFee_shouldReturnFirstFee() {
    }

    private void makePayment(ParkModel examplePark) {
        examplePark.setPrice(new Price(Currency.getInstance("PLN"), 2).markPaid());
        parkRepository.saveAndFlush(examplePark);
    }
}