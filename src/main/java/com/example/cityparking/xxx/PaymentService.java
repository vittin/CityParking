package com.example.cityparking.xxx;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Currency;

public interface PaymentService {
    @Transactional
    boolean charge(ParkModel parkModel, Currency currency);
    double calculatePrice(LocalDateTime startDate, Currency currency);
}
