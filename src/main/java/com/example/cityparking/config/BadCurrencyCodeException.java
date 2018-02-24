package com.example.cityparking.config;

public class BadCurrencyCodeException extends RuntimeException {

    public BadCurrencyCodeException(String currencyCode, IllegalArgumentException illegalCurrencyCode) {
        super(currencyCode, illegalCurrencyCode);
    }
}
