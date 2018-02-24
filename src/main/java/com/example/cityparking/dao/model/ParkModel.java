package com.example.cityparking.dao.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ParkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = CustomerModel.class, cascade = CascadeType.ALL)
    @NotNull
    @JsonManagedReference
    private CustomerModel customer;
    @NotNull
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @OneToOne(targetEntity = Price.class, cascade = CascadeType.ALL)
    private Price price;

    ParkModel(){}

    public ParkModel(CustomerModel customer, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ParkModel(CustomerModel customer, LocalDateTime startDate, LocalDateTime endDate, Price price) {
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
