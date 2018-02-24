package com.example.cityparking.payment;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface CashSummaryService {
    /**
     *
     * @param dateTime is selected date from past.
     * @return duty for all parked places, grouped by currency
     */
    Map<Currency, Double> getCashSummary(LocalDate dateTime);

    /**
     *
     * @param dateTime is selected date from past.
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return duty for all parked places, grouped by given currency
     */
    Double getCashSummary(LocalDate dateTime, Currency currency);
}
