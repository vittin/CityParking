package com.example.cityparking.owner;

import com.example.cityparking.dao.model.CustomerModel;
import com.example.cityparking.dao.model.ParkModel;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface ParkingMeterOwnerService {
    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @param parkEventId is unique id of parking event - each one contains start and (optionally) end date
     * @return true if payment was successful, false otherwise.
     */
    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @return customer entity
     */
    CustomerModel customerInfo(String customerIdentity);

    /**
     *
     * @param parkEventId is unique id of parking event - each one contains start and (optionally) end date
     * @return park event entity
     */
    ParkModel parkInfo(long parkEventId);

    /**
     *
     * @param date is the selected day from past.
     * @return all paid events between 00:00 and 23:59 of date, grouped by payment currency
     */
    Map<Currency, Double> cashSummary(LocalDate date);

    /**
     *
     * @param date date is the selected day from past.
     * @param currency is one on available payment currency
     * @return all paid events between 00:00 and 23:59 of date, payed by selected currency
     */
    Double cashSummary(LocalDate date, Currency currency);
}
