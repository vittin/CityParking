package com.example.cityparking.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Service
public class ParkingMeterOwnerServiceImpl implements ParkingMeterOwnerService {

    private CashSummaryService cashSummaryService;
    private CustomerRepository customerRepository;

    @Autowired
    public ParkingMeterOwnerServiceImpl(CashSummaryService cashSummaryService, CustomerRepository customerRepository) {
        this.cashSummaryService = cashSummaryService;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerModel customerInfo(String customerIdentity) {
        return customerRepository.findByIdentity(customerIdentity);
    }

    @Override
    public ParkModel parkInfo(long parkID) {
        return null;
    }

    @Override
    public Map<Currency, Integer> cashSummary(LocalDate date) {
        return cashSummaryService.getCashSummary();
    }
}
