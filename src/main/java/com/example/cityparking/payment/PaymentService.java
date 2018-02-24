package com.example.cityparking.payment;

import com.example.cityparking.dao.model.ParkModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

public interface PaymentService {
    /**
     *
     * @param parkModel is park event entity
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return true if payment are accepted - false otherwise
     */
    @Transactional //should be ;)
    boolean charge(ParkModel parkModel, Currency currency);

    /**
     *
     * @param parkModel is park event entity
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return duty for parking time from start to now
     */
    double calculatePrice(ParkModel parkModel, Currency currency);
}
