package com.example.cityparking.xxx;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ParkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer")
    CustomerModel customer;
    //@NotNull
    LocalDateTime startDate;
    LocalDateTime endDate;

    ParkModel(){}

    ParkModel(CustomerModel customer, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkModel parkModel = (ParkModel) o;
        return
                id == parkModel.id &&
                Objects.equals(customer, parkModel.customer) &&
                Objects.equals(startDate, parkModel.startDate) &&
                Objects.equals(endDate, parkModel.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, customer, startDate, endDate);
    }

    @Override
    public String toString() {
        return "ParkModel{" +
                "id=" + id +
                ", customer=" + customer.getId() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
