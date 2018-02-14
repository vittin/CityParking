package com.example.cityparking.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
public class PolishCurrency {

    @Bean
    public Currency plnCurrency(){
        return Currency.getInstance("PLN");
    }
}
