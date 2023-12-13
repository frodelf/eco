package com.example.eco.repo;

import com.example.eco.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
}
