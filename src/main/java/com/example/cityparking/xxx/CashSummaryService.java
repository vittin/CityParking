package com.example.cityparking.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CashSummaryService {
    private final Map<Currency, Integer> cash;

    @Autowired
    public CashSummaryService(List<Currency> availableCurrencies) {
        cash = new HashMap<>();
        availableCurrencies.forEach(k -> cash.put(k, 0));
    }

    public Map<Currency, Integer> getCashSummary() {
        return cash;
    }
}
