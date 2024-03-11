package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String area;
    private String soilType;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "terrain_seed_type",
            joinColumns = @JoinColumn(name = "terrain_id"),
            inverseJoinColumns = @JoinColumn(name = "seed_type_id"))
    private List<SeedType> seedTypes = new ArrayList<>();
    private String photo;
    private String email;
    private Long remainingDays;
    private boolean forSale;
    private String fullName;
}
