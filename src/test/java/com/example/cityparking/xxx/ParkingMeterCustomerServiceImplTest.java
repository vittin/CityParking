package com.example.cityparking.xxx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    public void when_callStartMethod_then_shouldCreateCustomerEvent() {
        customerService.start("John Snow");

        CustomerModel customerModel = customerRepository.findByIdentity("John Snow");

        assertThat(customerModel, is(not(nullValue())));
        assertThat(customerModel.getParkPlaces(), is(not(empty())));
    }

    @Test
    public void when_callStartMethod_then_shouldCreateParkEvent() {

        customerService.start("John Snow");

        List<ParkModel> parkModelList = parkRepository.findAllByCustomerIdentity("John Snow");

        assertThat(parkModelList, is(not(empty())));
        assertThat(parkModelList.get(0).getCustomer(), hasProperty("identity", equalTo("John Snow")));
    }
}