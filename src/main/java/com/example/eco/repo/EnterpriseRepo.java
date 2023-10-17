package com.example.eco.repo;

import com.example.eco.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepo extends JpaRepository<Enterprise, Long> {
}
