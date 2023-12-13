package com.example.eco.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Data
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;
    private String address;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "enterprise")
    @JsonBackReference
    @ToString.Exclude
    private List<Pollution> pollutionList;
}
