package com.example.cityparking.xxx;

import java.util.Currency;

public interface ParkingMeterCustomerService {
    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @return parkEventId
     */
    long start(String customerIdentity);

    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @param parkEventId is unique id of parking event - each one contains start and (optionally) end date
     * @return true if payment was successful, false otherwise.
     */
    boolean stop(String customerIdentity, long parkEventId);

    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @return duty for all parked places, excluding these that were already paid. Using default 'PLN' currency.
     */
    Price checkFee(String customerIdentity);

    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return duty for all parked places, conversed to given currency
     */
    Price checkFee(String customerIdentity, Currency currency); //sum of all parked cars

    /**
     *
     * @param customerIdentity is unique id, each user select it during registration
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @param parkEventId is unique id of parking event - each one contains start and (optionally) end date
     * @return duty for one parking event, conversed to given currency
     */
    Price checkFee(String customerIdentity, Currency currency, long parkEventId); //price for selected one
}
