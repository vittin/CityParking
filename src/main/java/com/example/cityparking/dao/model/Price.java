package com.example.cityparking.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Currency;
import java.util.Objects;

@Entity
public class Price {
    @Id
    @GeneratedValue
    private long id;
    private Currency currency;
    private Double value;
    private boolean paid;

    public Price(){}

    public Price(Currency paymentCurrency, Double value) {
        this.currency = paymentCurrency;
        this.value = value;
        this.paid = false;
    }

    public Price(Currency paymentCurrency, Integer value) {
        this.currency = paymentCurrency;
        this.value = new Double(value);
        this.paid = false;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency paymentCurrency) {
        this.currency = paymentCurrency;
    }

    public Double getValue() {
        return value;
    }

    public Price markPaid(){
        this.paid = true;
        return this;
    }

    public boolean isPaid(){
        return paid;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", currency=" + currency +
                ", value=" + value +
                ", paid=" + paid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return id == price.id &&
                paid == price.paid &&
                Objects.equals(currency, price.currency) &&
                Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, currency, value, paid);
    }
}
