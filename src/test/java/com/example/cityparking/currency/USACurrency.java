package com.example.cityparking.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
public class USACurrency {
    @Bean
    Currency usdCurrency(){
        return Currency.getInstance("USD");
    }
}
