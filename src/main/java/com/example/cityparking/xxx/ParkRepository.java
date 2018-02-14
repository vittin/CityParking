package com.example.cityparking.xxx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkRepository  extends JpaRepository<ParkModel, Long> {
    List<ParkModel> findByCustomerIdentity(String customerIdentity);
}