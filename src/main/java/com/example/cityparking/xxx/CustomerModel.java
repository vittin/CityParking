package com.example.cityparking.xxx;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(unique = true)
    String identity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ParkModel.class, fetch = FetchType.EAGER)
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

    public void addParkPlace(ParkModel parkModel) {
        if (parkPlaces == null){
            parkPlaces = new ArrayList<>();
        }
        parkPlaces.add(parkModel);
    }

    public List<ParkModel> getParkPlaces() {
        return parkPlaces;
    }

    public void setParkPlaces(List<ParkModel> parkPlaces) {
        this.parkPlaces = parkPlaces;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", parkPlaces=" + parkPlaces +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerModel)) return false;
        CustomerModel that = (CustomerModel) o;
        return  Objects.equals(identity, that.identity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(identity);
    }
}