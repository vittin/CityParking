package com.example.cityparking.xxx;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String identity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
    List<ParkModel> parkPlaces;

    CustomerModel(){}

    CustomerModel(String identity, List<ParkModel> parkPlaces) {
        this.identity = identity;
        this.parkPlaces = parkPlaces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<ParkModel> getParkPlaces() {
        return parkPlaces;
    }

    public void setParkPlaces(List<ParkModel> parkPlaces) {
        this.parkPlaces = parkPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return
                id == that.id &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(parkPlaces, that.parkPlaces);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, identity, parkPlaces);
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", parkPlaces=" + parkPlaces +
                '}';
    }
}
