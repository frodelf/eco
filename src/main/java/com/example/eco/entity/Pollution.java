package com.example.eco.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import static java.lang.Math.pow;

@Entity
@Data
public class Pollution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    private String unitOfMeasurement;
    private Double quantity;
    private Double concentration;
    private Double carcinogenicRisk;
    private Double nonCarcinogenicRisk;
    private String compensation;
    private Double impost;
    @ManyToOne
    @JsonManagedReference
    private Enterprise enterprise;
    @ManyToOne
    @JsonManagedReference
    private Pollutant pollutant;

    public void setConcentration(Double concentration) {
        this.carcinogenicRisk = carcinogenicRisk(concentration);
        this.nonCarcinogenicRisk = concentration / pollutant.getRFC();
        this.concentration = concentration;
    }
    private double carcinogenicRisk(Double concentration){
        double LADD = ( (concentration * pow(10, -6) * 8 * 1.4)
                + (1.0 * concentration * pow(10, -6) * 16 * 0.63) ) * 350 * 30 / (70 * 70 * 365);
        return LADD * pollutant.getFS();
    }
}
