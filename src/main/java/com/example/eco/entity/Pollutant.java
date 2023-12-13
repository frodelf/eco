package com.example.eco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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
    private Double RFC;
    private Integer FS;
    private Double impost;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pollutant")
    @JsonBackReference
    @ToString.Exclude
    private List<Pollution> pollutionList;
}
