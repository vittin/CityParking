package com.example.cityparking.xxx;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ParkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne(targetEntity = CustomerModel.class)
    CustomerModel customer;
    @NotNull
    LocalDateTime startDate;
    LocalDateTime endDate;
    @OneToOne(targetEntity = Price.class, cascade = CascadeType.ALL)
    Price price;

    ParkModel(){}

    ParkModel(CustomerModel customer, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    ParkModel(CustomerModel customer, LocalDateTime startDate, LocalDateTime endDate, Price price) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ParkModel{" +
                "id=" + id +
                ", customer=" + customer.getIdentity() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkModel)) return false;
        ParkModel parkModel = (ParkModel) o;
        return id == parkModel.id &&
                Objects.equals(customer.getId(), parkModel.customer.getId()) &&
                Objects.equals(startDate, parkModel.startDate) &&
                Objects.equals(endDate, parkModel.endDate) &&
                Objects.equals(price, parkModel.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, customer, startDate, endDate, price);
    }
}
