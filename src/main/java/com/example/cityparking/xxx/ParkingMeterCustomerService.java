package com.example.cityparking.xxx;

public interface ParkingMeterCustomerService {
    void start(String carID);
    void stop(String carID);
    void checkFee(String carID);
}
