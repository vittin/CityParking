package com.example.cityparking.xxx;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface CashSummaryService {
    Map<Currency, Double> getCashSummary(LocalDate dateTime);
    Double getCashSummary(LocalDate dateTime, Currency currency);
}
