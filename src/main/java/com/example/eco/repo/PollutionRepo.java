package com.example.eco.repo;

import com.example.eco.entity.Pollution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollutionRepo extends JpaRepository<Pollution, Long> {
}
