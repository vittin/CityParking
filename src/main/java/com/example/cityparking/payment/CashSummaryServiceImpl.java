package com.example.cityparking.payment;

import com.example.cityparking.dao.model.ParkModel;
import com.example.cityparking.dao.ParkRepository;
import com.example.cityparking.dao.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CashSummaryServiceImpl implements CashSummaryService {

    private final ParkRepository parkRepository;

    @Autowired
    public CashSummaryServiceImpl(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    @Override
    public Map<Currency, Double> getCashSummary(LocalDate dateTime) {
        List<ParkModel> parkEvents = getEvents(dateTime);

        Map<Currency, Double> cashSummary = new HashMap<>();
        Set<Currency> availableCurrencies = parkEvents.stream()
                .map(e -> Optional.ofNullable(e.getPrice())
                        .map(Price::getCurrency)
                        .orElse(Currency.getInstance("PLN")))
                .collect(Collectors.toSet());

        //todo: im pretty sure that it can be done with one stream - fix later
        availableCurrencies.forEach(c -> cashSummary.put(c, summarizeCash(c, parkEvents)));
        return cashSummary;
    }

    @Override
    public Double getCashSummary(LocalDate dateTime, Currency currency) {
        List<ParkModel> parkEvents = getEvents(dateTime);
        return summarizeCash(currency, parkEvents);
    }

    private Double summarizeCash(Currency currency, List<ParkModel> parkEvents) {
        return parkEvents.stream()
                .map(ParkModel::getPrice)
                .filter(p -> currency.equals(p.getCurrency()))
                .mapToDouble(Price::getValue)
                .sum();
    }

    private List<ParkModel> getEvents(LocalDate localDate){
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);

        return parkRepository.findAllByEndDateIsBetweenAndPriceIsNotNull(
                startOfDay,
                endOfDay
        );
    }


}
