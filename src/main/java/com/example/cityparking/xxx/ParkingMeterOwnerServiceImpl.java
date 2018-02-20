package com.example.cityparking.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Service
public class ParkingMeterOwnerServiceImpl implements ParkingMeterOwnerService {

    private CashSummaryServiceImpl cashSummaryService;
    private CustomerRepository customerRepository;
    private ParkRepository parkRepository;

    @Autowired
    public ParkingMeterOwnerServiceImpl(CashSummaryServiceImpl cashSummaryService, CustomerRepository customerRepository, ParkRepository parkRepository) {
        this.cashSummaryService = cashSummaryService;
        this.customerRepository = customerRepository;
        this.parkRepository = parkRepository;
    }

    @Override
    public CustomerModel customerInfo(String customerIdentity) {
        return customerRepository.findByIdentity(customerIdentity);
    }

    @Override
    public ParkModel parkInfo(long parkID) {
        return parkRepository.findOne(parkID);
    }

    @Override
    public Map<Currency, Double> cashSummary(LocalDate date) {
        return cashSummaryService.getCashSummary(date);
    }

    @Override
    public Double cashSummary(LocalDate date, Currency currency) {
        return cashSummaryService.getCashSummary(date, currency);
    }
}
