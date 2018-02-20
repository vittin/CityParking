package com.example.cityparking.xxx;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface ParkingMeterOwnerService {
    CustomerModel customerInfo(String customerIdentity);
    ParkModel parkInfo(long parkID);
    Map<Currency, Double> cashSummary(LocalDate date);
    Double cashSummary(LocalDate date, Currency currency);
}
