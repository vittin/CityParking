package com.example.cityparking.dao;

import com.example.cityparking.dao.model.ParkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkRepository  extends JpaRepository<ParkModel, Long> {
    List<ParkModel> findAllByCustomerIdentity(String customerIdentity);

    List<ParkModel> findAllByEndDateIsBetween(LocalDateTime from, LocalDateTime to);

    List<ParkModel> findAllByEndDateIsBetweenAndPriceIsNotNull(LocalDateTime from, LocalDateTime to);
}
