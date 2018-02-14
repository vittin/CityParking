package com.example.cityparking.xxx;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface ParkingMeterOwnerService {
    CustomerModel customerInfo(String customerIdentity);
    ParkModel parkInfo(long parkID);
    Map<Currency, Integer> cashSummary(LocalDate date);
}
