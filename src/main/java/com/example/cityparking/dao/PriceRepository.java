package com.example.cityparking.dao;

import com.example.cityparking.dao.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
