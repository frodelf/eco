package com.example.eco.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pollution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    private String unitOfMeasurement;
    private Double quantity;
    @ManyToOne
    private Enterprise enterprise;
    @ManyToOne
    private Pollutant pollutant;
}
