package com.example.cityparking.dao.model;

import java.util.Arrays;
import java.util.List;

public enum CustomerType {

    REGULAR(Arrays.asList(1.0,2.0), 2), VIP(Arrays.asList(0.0, 2.0), 1.5);

    private final List<Double> prices;
    private final double multiplier;

    CustomerType(List<Double> prices, double multiplier){
        this.prices = prices;
        this.multiplier = multiplier;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String toShortName(CustomerType customerType) {
        switch (customerType) {
            case REGULAR: return "R";
            case VIP: return "V";
            default: return "R";
        }
    }

    public static CustomerType fromShortName(String customerType){
        switch (customerType) {
            case "R": return REGULAR;
            case "V": return VIP;
            default: return REGULAR;
        }
    }
}
