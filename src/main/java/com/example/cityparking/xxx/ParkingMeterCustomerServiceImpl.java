package com.example.cityparking.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;

@Service
public class ParkingMeterCustomerServiceImpl implements ParkingMeterCustomerService {

    private PaymentService paymentService;
    private CustomerRepository customerRepository;
    private ParkRepository parkRepository;

    @Autowired
    public ParkingMeterCustomerServiceImpl(PaymentService paymentService, CustomerRepository customerRepository, ParkRepository parkRepository) {
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.parkRepository = parkRepository;
    }

    @Override
    public long start(String customerIdentity) {
        CustomerModel customerModel = getCustomerByIdentity(customerIdentity);
        ParkModel parkEvent = new ParkModel(customerModel, LocalDateTime.now(), null);
        customerModel.addParkPlace(parkEvent);
        customerRepository.saveAndFlush(customerModel);
        return parkEvent.getId();
    }

    @Override
    public boolean stop(String customerIdentity, long parkEventId) {
        CustomerModel customerModel = getCustomerByIdentity(customerIdentity);
        ParkModel parkModel = parkRepository.findOne(parkEventId);
        if (parkModel != null && parkModel.getPrice() != null && parkModel.getPrice().isPaid()){
            parkModel.setEndDate(LocalDateTime.now());
            parkRepository.saveAndFlush(parkModel);
            return true;
        }
        return false;
    }

    @Override
    public Price checkFee(String customerIdentity) {
        return checkFee(customerIdentity, Currency.getInstance("PLN"));
    }

    @Override
    public Price checkFee(String customerIdentity, Currency currency) {
        double summarizePrice = parkRepository.findAllByCustomerIdentity(customerIdentity).stream()
                .filter(e -> e.getPrice() == null || !e.getPrice().isPaid())
                .mapToDouble(e -> paymentService.calculatePrice(e.getStartDate(), Currency.getInstance("PLN")))
                .sum();
        return new Price(currency, summarizePrice);
    }

    @Override
    public Price checkFee(String customerIdentity, Currency currency, long parkEventId) {
        ParkModel parkEvent = parkRepository.findOne(parkEventId);
        if (parkEvent == null || parkEvent.getPrice().isPaid()){
            return new Price(currency, 0);
        }
        return new Price(currency, paymentService.calculatePrice(parkEvent.startDate, currency));

    }

    private CustomerModel getCustomerByIdentity(String customerIdentity) {
        CustomerModel customerModel = customerRepository.findByIdentity(customerIdentity);
        if (customerModel == null){
            customerModel = new CustomerModel(customerIdentity, new ArrayList<>());
        }
        return customerModel;
    }
}
