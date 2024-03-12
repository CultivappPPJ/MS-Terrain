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
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "terrain_seed_types",
            joinColumns = {@JoinColumn(name = "terrain_id")},
            inverseJoinColumns = {@JoinColumn(name = "seed_type_id")})
    private Set<SeedType> seedTypes = new HashSet<>();
}
