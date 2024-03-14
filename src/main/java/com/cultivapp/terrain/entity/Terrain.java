package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "terrain")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String area;
    private String soilType;
    private String photo;
    private String email;
    private Long remainingDays;
    private boolean forSale;
    private String fullName;
    public boolean getForSale() {
        return this.forSale;
    }
}
