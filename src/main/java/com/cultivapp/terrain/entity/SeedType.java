package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seed_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "seedType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TerrainSeedType> terrains = new HashSet<>();
}