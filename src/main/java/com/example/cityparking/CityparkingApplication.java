package com.example.cityparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CityparkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityparkingApplication.class, args);
	}
}
