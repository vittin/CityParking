package com.example.cityparking.xxx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ParkRepository parkRepository;

    @Autowired
    public PaymentServiceImpl(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    @Override
    public boolean charge(ParkModel parkModel, Currency currency) {
        double priceValue = calculatePrice(parkModel.getStartDate(), currency);
        Price price = new Price(currency, priceValue);
        parkModel.setPrice(price);
        if (chargeClient(parkModel.getCustomer().getId(), price)){
            parkModel.getPrice().markPaid();
            parkRepository.saveAndFlush(parkModel);
            return true;
        }
        return false;
    }

    @Override
    public double calculatePrice(LocalDateTime startDate, Currency currency) {
        long parkedTimeInMinutes = ChronoUnit.MINUTES.between(startDate, LocalDateTime.now());
        double priceValue = calculatePriceValue(parkedTimeInMinutes);
        return priceValue * getCurrencyMultiplier(currency);
    }


    private boolean chargeClient(long customerId, Price price) {
        return externalServicePayment(customerId, price);
    }

    private double getCurrencyMultiplier(Currency currency) {
        switch (currency.getSymbol()) {
            case "USD":
                return 3.8;
            default:
                return 1.0;
        }
    }

    private double calculatePriceValue(long parkedTimeInMinutes) {
        return 3 * (parkedTimeInMinutes/60);
    }

    private boolean externalServicePayment(long id, Price price) {
        logger.info("Payment successful. User {} charged for: {} {}.", id, price.getValue(), price.getCurrency());
        return true;
    }
}
