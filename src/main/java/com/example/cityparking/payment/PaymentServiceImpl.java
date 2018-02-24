package com.example.cityparking.payment;

import com.example.cityparking.dao.model.CustomerType;
import com.example.cityparking.dao.model.ParkModel;
import com.example.cityparking.dao.ParkRepository;
import com.example.cityparking.dao.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ParkRepository parkRepository;

    @Autowired
    public PaymentServiceImpl(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    @Override
    public boolean charge(ParkModel parkModel, Currency currency) {
        double priceValue = calculatePrice(parkModel, currency);
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
    public double calculatePrice(ParkModel parkModel, Currency currency) {
        CustomerType customerType = parkModel.getCustomer().getCustomerType();
        int parkedTimeInHours = parkedTimeInHours(parkModel.getStartDate());
        double priceValue = calculatePriceValue(customerType, parkedTimeInHours);
        return priceValue * getCurrencyMultiplier(currency);
    }

    private int parkedTimeInHours(LocalDateTime startDate) {
        long parkedTimeInMinutes = ChronoUnit.MINUTES.between(startDate, LocalDateTime.now());
        //assuming that each 60min park start next park hour;
        int fullHours = (int) parkedTimeInMinutes / 60;
        return fullHours + 1;
    }


    private boolean chargeClient(long customerId, Price price) {
        return externalServicePayment(customerId, price);
    }

    /* todo: integrate with api.
    /  todo: @see http://fixer.io/ for more info
    */
    private double getCurrencyMultiplier(Currency currency) {
        double exchangeRate = 1.0;
        switch (currency.getSymbol()) {
            case "USD":
                exchangeRate = (1/3.8);
                logger.debug("Converting to USD using exchange rate 1:{}", exchangeRate);
                break;
        }
        return exchangeRate;
    }

    private double calculatePriceValue(CustomerType customerType, int parkedTimeInHours) {
        double cost = 0.0;
        double lastHourFee = customerType.getPrices().get(customerType.getPrices().size() - 1); //last fixed fee
        for (int i = 0; i < parkedTimeInHours; i++){
            if (i < customerType.getPrices().size()){
                cost += customerType.getPrices().get(i);
            } else {
                lastHourFee *= customerType.getMultiplier();
                cost += lastHourFee;
            }
        }
        return cost;
    }

    //todo: is stub ;)
    private boolean externalServicePayment(long id, Price price) {
        logger.info("Payment successful. User {} charged for: {} {}.", id, price.getValue(), price.getCurrency());
        return true;
    }
}
