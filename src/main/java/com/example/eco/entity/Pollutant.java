package com.example.eco.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Data
public class Pollutant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unitOfMeasurement;
    private Double mpc;
    private Integer dangerous;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pollutant")
    private List<Pollution> pollutionList;
}
