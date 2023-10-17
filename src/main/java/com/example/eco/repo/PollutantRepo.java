package com.example.eco.repo;

import com.example.eco.entity.Pollutant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollutantRepo extends JpaRepository<Pollutant, Long> {
}
