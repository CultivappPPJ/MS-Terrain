package com.cultivapp.terrain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String area;
    private String soilType;
    private String plantType;
    private String photo;
    private String contactEmail;
    private Long remainingDays;
    private Boolean forSale;
    private String fullName;
}


